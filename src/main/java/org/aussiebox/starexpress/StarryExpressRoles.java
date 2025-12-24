package org.aussiebox.starexpress;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import org.agmas.harpymodloader.Harpymodloader;

import java.util.HashMap;

public class StarryExpressRoles {

    private static final HashMap<String, Role> ROLES = new HashMap<>();

    public static HashMap<String, Role> getRoles() {
        return ROLES;
    }

    public static Role STARSTRUCK = registerRole(new Role(
            StarryExpress.id("starstruck"),
            0x5747ff,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime() + 100, // Civilian sprint time + 5 seconds
            false
    ));

    public static void init() {

        /// STARSTRUCK
        Harpymodloader.setRoleMaximum(STARSTRUCK, 1);

    }

    public static Role registerRole(Role role) {
        WatheRoles.registerRole(role);
        ROLES.put(role.identifier().getPath(), role);
        return role;
    }

}
