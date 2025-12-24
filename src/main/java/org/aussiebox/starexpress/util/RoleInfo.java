package org.aussiebox.starexpress.util;

public final class RoleInfo {
    private final String roleNamespace;
    private final RoleType roleType;
    private final int roleColor;
    private final GuidebookEntry guidebookEntry;

    public RoleInfo(String namespace, RoleType type, int color, GuidebookEntry entry) {
        this.roleNamespace = namespace;
        this.roleType = type;
        this.roleColor = color;
        this.guidebookEntry = entry;
    }

    public String namespace() {
        return this.roleNamespace;
    }

    public RoleType type() {
        return this.roleType;
    }

    public int color() {
        return this.roleColor;
    }

    public GuidebookEntry guidebookEntry() {
        return this.guidebookEntry;
    }

    public enum RoleType {
        ROLE,
        MODIFIER;

        RoleType() {
        }
    }

    public enum GuidebookEntry {
        GOOD,
        NEUTRAL,
        EVIL,
        NONE;

        GuidebookEntry() {
        }
    }
}
