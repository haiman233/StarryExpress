package org.aussiebox.starexpress.config;

import blue.endless.jankson.Comment;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Config(name = "starexpress-server", wrapperName = "StarryExpressServerConfig")
public class ServerConfig {

    @SectionHeader("role_config")

    @Comment("Config options related to the Starstruck role.")
    @Nest public StarstruckConfig starstruckConfig = new StarstruckConfig();
    @Comment("Config options related to the Allergic modifier.")
    @Nest public AllergicConfig allergicConfig = new AllergicConfig();

    public static class StarstruckConfig {

        @Comment("When enabled, completing a task as the Starstruck will reduce its ability cooldown.")
        public boolean taskReducesCooldown = true;

        @Comment("The number of seconds to reduce the Starstruck's ability cooldown by upon task completion.")
        @RangeConstraint(min = 1, max = 600)
        public int taskCooldownReduction = 5;

        @Comment("The Starstruck's ability cooldown, in seconds.")
        @RangeConstraint(min = 1, max = 600)
        public int abilityCooldown = 90;

        @Comment("The Starstruck's ability duration, in seconds.")
        @RangeConstraint(min = 1, max = 600)
        public int abilityDuration = 15;

        @Comment("When enabled, the Starstruck's movement speed will be changed while their ability is active.")
        public boolean abilityAffectsMovementSpeed = true;

        @Comment("The Starstruck's movement speed while walking with their ability active. (Default: 0.12F, WATHE Default: 0.07F)")
        @RangeConstraint(min = 0.07F, max = 10.0F)
        public float abilityWalkSpeed = 0.12F;

        @Comment("The Starstruck's movement speed while sprinting with their ability active. (Default: 0.15F, WATHE Default: 0.1F)")
        @RangeConstraint(min = 0.1F, max = 10.0F)
        public float abilitySprintSpeed = 0.15F;

    }
    public static class AllergicConfig {

        @Comment("The chance of Allergic players receiving no effects upon their allergy triggering. Set to 0 to disable.")
        public int nothingChance = 3;

        @Comment("The chance of Allergic players receiving instinct upon their allergy triggering. Set to 0 to disable.")
        public int instinctChance = 1;

        @Comment("The chance of Allergic players receiving armor upon their allergy triggering. Set to 0 to disable.")
        public int armorChance = 1;

        @Comment("The chance of Allergic players being poisoned upon their allergy triggering. Set to 0 to disable.")
        public int poisonChance = 1;

        @Comment("The duration, in seconds, of the Allergic's instinct effect.")
        public int instinctDuration = 3;

    }
}