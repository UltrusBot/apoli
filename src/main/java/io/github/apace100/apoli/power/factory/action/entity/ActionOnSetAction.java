package io.github.apace100.apoli.power.factory.action.entity;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.EntitySetPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionOnSetAction {

    public static void action(SerializableData.Instance data, Entity entity) {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
        PowerType<?> powerType = data.get("set");
        Power p = component.getPower(powerType);
        if(p instanceof EntitySetPower entitySetPower) {
            Consumer<Pair<Entity, Entity>> action = data.get("bientity_action");
            Predicate<Pair<Entity, Entity>> condition = data.get("bientity_condition");
            if(condition == null) {
                condition = pair -> true;
            }
            List<UUID> list = entitySetPower.getIterationSet().stream().toList();
            if(data.getBoolean("reverse")) {
                Collections.reverse(list);
            }
            boolean breakAfterFirst = data.get("match") == Match.FIRST;
            for(UUID uuid : list) {
                Entity e = entitySetPower.getEntity(uuid);
                Pair<Entity, Entity> entityPair = new Pair<>(entity, e);
                if(condition.test(entityPair)) {
                    action.accept(entityPair);
                    if(breakAfterFirst) {
                        break;
                    }
                }
            }
        }
    }

    public enum Match {
        ALL, FIRST
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(Apoli.identifier("action_on_set"),
            new SerializableData()
                    .add("set", ApoliDataTypes.POWER_TYPE)
                    .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION)
                    .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                    .add("match", SerializableDataType.enumValue(Match.class), Match.ALL)
                    .add("reverse", SerializableDataTypes.BOOLEAN, false),
                ActionOnSetAction::action
        );
    }
}
