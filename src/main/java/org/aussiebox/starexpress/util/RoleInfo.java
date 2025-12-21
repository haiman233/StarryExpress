package org.aussiebox.starexpress.util;

public final class RoleInfo {
    private final RoleType roleType;
    private final int roleColor;
    private final GuidebookEntry guidebookEntry;

    public RoleInfo(RoleType type, int color, GuidebookEntry entry) {
        this.roleType = type;
        this.roleColor = color;
        this.guidebookEntry = entry;
    }

    public RoleType roleType() {
        return this.roleType;
    }

    public int roleColor() {
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
