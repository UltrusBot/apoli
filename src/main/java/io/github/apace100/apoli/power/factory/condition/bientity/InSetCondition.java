package io.github.apace100.apoli.power.factory.condition.bientity;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.EntitySetPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;

public class InSetCondition {

    public static boolean condition(SerializableData.Instance data, Pair<Entity, Entity> pair) {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(pair.getLeft());
        PowerType<?> powerType = data.get("set");
        Power p = component.getPower(powerType);
        if(p instanceof EntitySetPower entitySetPower) {
            return entitySetPower.contains(pair.getRight());
        }
        return false;
    }

    public static ConditionFactory<Pair<Entity, Entity>> getFactory() {
        return new ConditionFactory<>(Apoli.identifier("in_set"),
            new SerializableData()
                    .add("set", ApoliDataTypes.POWER_TYPE),
            InSetCondition::condition
        );
    }
}