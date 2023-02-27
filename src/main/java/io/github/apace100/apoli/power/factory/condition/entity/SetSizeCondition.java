package io.github.apace100.apoli.power.factory.condition.entity;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.EntitySetPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Pair;

import java.util.UUID;
import java.util.function.Predicate;

public class SetSizeCondition {

    public static boolean condition(SerializableData.Instance data, Entity entity) {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
        PowerType<?> powerType = data.get("set");
        Power p = component.getPower(powerType);
        int value = 0;
        if(p instanceof EntitySetPower entitySetPower) {
            value = entitySetPower.size();
        }
        return ((Comparison) data.get("comparison")).compare(value, data.getInt("compare_to"));
    }

    public static ConditionFactory<Entity> getFactory() {
        return new ConditionFactory<>(Apoli.identifier("set_size"),
            new SerializableData()
                    .add("set", ApoliDataTypes.POWER_TYPE)
                    .add("comparison", ApoliDataTypes.COMPARISON)
                    .add("compare_to", SerializableDataTypes.INT),
                SetSizeCondition::condition
        );
    }
}
