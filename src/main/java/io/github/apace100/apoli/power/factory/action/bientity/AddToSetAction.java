package io.github.apace100.apoli.power.factory.action.bientity;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.EntitySetPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;

public class AddToSetAction {

    public static void action(SerializableData.Instance data, Pair<Entity, Entity> entities) {

        PowerHolderComponent component = PowerHolderComponent.KEY.maybeGet(entities.getLeft()).orElse(null);
        PowerType<?> powerType = data.get("set");

        if (component == null || powerType == null) {
            return;
        }

        Power power = component.getPower(powerType);
        if (power instanceof EntitySetPower entitySetPower) {
            entitySetPower.add(entities.getRight());
        }

    }

    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(
            Apoli.identifier("add_to_set"),
            new SerializableData()
                .add("set", ApoliDataTypes.POWER_TYPE),
            AddToSetAction::action
        );
    }
}
