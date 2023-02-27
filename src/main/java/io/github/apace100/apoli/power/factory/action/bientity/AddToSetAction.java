package io.github.apace100.apoli.power.factory.action.bientity;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.EntitySetPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.util.Pair;

public class AddToSetAction {

    public static void action(SerializableData.Instance data, Pair<Entity, Entity> entities) {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entities.getLeft());
        PowerType<?> powerType = data.get("set");
        Power p = component.getPower(powerType);
        if(p instanceof EntitySetPower entitySetPower) {
            entitySetPower.add(entities.getRight());
        }
    }

    public static ActionFactory<Pair<Entity, Entity>> getFactory() {
        return new ActionFactory<>(Apoli.identifier("add_to_set"),
            new SerializableData()
                .add("set", ApoliDataTypes.POWER_TYPE),
            AddToSetAction::action
        );
    }
}
