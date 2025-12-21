package org.aussiebox.starexpress.client.gui.screen;

import dev.doctor4t.wathe.api.WatheRoles;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.CollapsibleContainer;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.ScrollContainer;
import io.wispforest.owo.ui.core.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.aussiebox.starexpress.StarryExpress;
import org.aussiebox.starexpress.util.RoleInfo;
import org.aussiebox.starexpress.util.RoleInfo.GuidebookEntry;
import org.aussiebox.starexpress.util.RoleInfo.RoleType;
import org.jetbrains.annotations.NotNull;
import pro.fazeclan.river.stupid_express.SEModifiers;
import pro.fazeclan.river.stupid_express.SERoles;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GuidebookScreen extends BaseOwoScreen<FlowLayout> {
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::horizontalFlow);
    }

    public Map<String, RoleInfo> roleInfo = new TreeMap<>();
    public Map<String, RoleInfo> getRoleInfo() {
        roleInfo.putIfAbsent("wathe:civilian", new RoleInfo(RoleType.ROLE, WatheRoles.CIVILIAN.color(), GuidebookEntry.GOOD));
        roleInfo.putIfAbsent("wathe:vigilante", new RoleInfo(RoleType.ROLE, WatheRoles.VIGILANTE.color(), GuidebookEntry.GOOD));
        roleInfo.putIfAbsent("wathe:killer", new RoleInfo(RoleType.ROLE, WatheRoles.KILLER.color(), GuidebookEntry.EVIL));
        if (FabricLoader.getInstance().isModLoaded("stupid_express")) {
            roleInfo.putIfAbsent("stupidexpress:amnesiac", new RoleInfo(RoleType.ROLE, SERoles.AMNESIAC.color(), GuidebookEntry.NEUTRAL));
            roleInfo.putIfAbsent("stupidexpress:arsonist", new RoleInfo(RoleType.ROLE, SERoles.ARSONIST.color(), GuidebookEntry.NEUTRAL));
            roleInfo.putIfAbsent("stupidexpress:necromancer", new RoleInfo(RoleType.ROLE, SERoles.NECROMANCER.color(), GuidebookEntry.EVIL));
            roleInfo.putIfAbsent("stupidexpress:avaricious", new RoleInfo(RoleType.ROLE, SERoles.AVARICIOUS.color(), GuidebookEntry.EVIL));
            roleInfo.putIfAbsent("stupidexpress:lovers", new RoleInfo(RoleType.MODIFIER, SEModifiers.LOVERS.color(), GuidebookEntry.NONE));
            roleInfo.putIfAbsent("stupidexpress:allergic", new RoleInfo(RoleType.MODIFIER, SEModifiers.ALLERGIC.color(), GuidebookEntry.NONE));
        }
        return roleInfo;
    }

    public Map<String, ButtonComponent> roleButtons = new HashMap<>();

    private String displayedEntry;
    private ButtonComponent displayedEntryButton;

    private final ScrollContainer<FlowLayout> roleButtonList = Containers.verticalScroll(Sizing.expand(40), Sizing.fill(), Containers.verticalFlow(Sizing.content(), Sizing.content()));
    private ScrollContainer<FlowLayout> currentRoleButtonList;

    private final FlowLayout informationFlow = Containers.verticalFlow(Sizing.expand(50), Sizing.fill());
    private FlowLayout currentInformationFlow;

    @Override
    protected void build(FlowLayout root) {
        getRoleInfo();
        setRoleButtonList(roleButtonList);
        setInformationFlow(informationFlow);
        root.surface(Surface.VANILLA_TRANSLUCENT);
        root.child(getRoleButtonList()).padding(Insets.of(10));
        root.child(getInformationFlow()).padding(Insets.of(10));
    }

    public FlowLayout getInformationFlow() {
        return this.currentInformationFlow;
    }

    public void setInformationFlow(FlowLayout layout) {
        this.currentInformationFlow = layout;
        this.currentInformationFlow.clearChildren();

        ScrollContainer<FlowLayout> roleDescription = Containers.verticalScroll(Sizing.fill(), Sizing.fill(), Containers.verticalFlow(Sizing.fill(), Sizing.fill()));
        FlowLayout scrollLayout = (FlowLayout) roleDescription.children().getFirst();
        scrollLayout.child(Components.label(Component.empty()).horizontalTextAlignment(HorizontalAlignment.LEFT).sizing(Sizing.fill(), Sizing.content()).id("role_description"));

        this.currentInformationFlow.child(Components.label(Component.empty().withStyle(Style.EMPTY.withFont(StarryExpress.id("guidebook_heading")))).lineHeight(18).horizontalTextAlignment(HorizontalAlignment.LEFT).sizing(Sizing.fill(), Sizing.content()).margins(Insets.bottom(10)).id("role_name"));
        this.currentInformationFlow.child(roleDescription).id("role_description_container");
    }

    public ScrollContainer<FlowLayout> getRoleButtonList() {
        return this.currentRoleButtonList;
    }

    public void setRoleButtonList(ScrollContainer<FlowLayout> container) {
        this.currentRoleButtonList = container;
        FlowLayout layout = (FlowLayout) this.currentRoleButtonList.children().getFirst();
        layout.clearChildren();
        roleButtons.clear();

        CollapsibleContainer rolesContainer = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("guidebook.category.roles"), true);
            CollapsibleContainer goodRolesContainer = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("guidebook.category.roles.good"), false);
            CollapsibleContainer neutralRolesContainer = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("guidebook.category.roles.neutral"), false);
            CollapsibleContainer evilRolesContainer = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("guidebook.category.roles.evil"), false);
        CollapsibleContainer modifiersContainer = Containers.collapsible(Sizing.fill(), Sizing.content(), Component.translatable("guidebook.category.modifiers"), false);

        for (String roleID : roleInfo.keySet()) {
            RoleInfo roleData = roleInfo.get(roleID);

            ButtonComponent button = Components.button(
                            Component.translatable("guidebook.role." + roleID).withColor(0xFFFFFF).append("                                                                                         "),
                            buttonComponent -> setDisplayedEntry(roleID)
            ).renderer(ButtonComponent.Renderer.texture(StarryExpress.id("textures/empty.png"), 0, 0, 1, 1));
            if (roleData.roleType() == RoleType.ROLE) {
                if (roleData.guidebookEntry() == GuidebookEntry.GOOD) {
                    goodRolesContainer.child(button.sizing(Sizing.content(), Sizing.content()).id(roleID));
                }
                if (roleData.guidebookEntry() == GuidebookEntry.NEUTRAL) {
                    neutralRolesContainer.child(button.sizing(Sizing.content(), Sizing.content()).id(roleID));
                }
                if (roleData.guidebookEntry() == GuidebookEntry.EVIL) {
                    evilRolesContainer.child(button.sizing(Sizing.content(), Sizing.content()).id(roleID));
                }
            } else if (roleData.roleType() == RoleType.MODIFIER) {
                modifiersContainer.child(button.sizing(Sizing.content(), Sizing.content()).id(roleID));
            }
            roleButtons.putIfAbsent(roleID, button);
        }

        FlowLayout rolesLayout = Containers.verticalFlow(Sizing.content(), Sizing.content());
        rolesLayout.child(goodRolesContainer).id("category.roles.good");
        rolesLayout.child(neutralRolesContainer).id("category.roles.neutral");
        rolesLayout.child(evilRolesContainer).id("category.roles.evil");

        rolesContainer.child(rolesLayout).id("category.roles-layout");

        layout.child(rolesContainer).id("category.roles");
        layout.child(modifiersContainer).id("category.modifiers");
    }

    public void setDisplayedEntry(String roleID) {
        this.displayedEntry = roleID;
        if (this.displayedEntryButton != null) {
            ButtonComponent oldButton = this.displayedEntryButton;
            oldButton.setMessage(oldButton.getMessage().copy().withColor(0xFFFFFF));
        }

        ButtonComponent newButton = roleButtons.get(roleID);
        newButton.setMessage(newButton.getMessage().copy().withColor(roleInfo.get(roleID).roleColor()));

        LabelComponent roleName = this.currentInformationFlow.childById(LabelComponent.class, "role_name");
        LabelComponent roleDescription = this.currentInformationFlow.childById(LabelComponent.class, "role_description");
        roleName.text(Component.translatable("guidebook.role." + roleID).withColor(roleInfo.get(roleID).roleColor()).withStyle(Style.EMPTY.withFont(StarryExpress.id("guidebook_heading"))));
        roleDescription.text(Component.translatable("guidebook.role.description." + roleID));

        this.displayedEntryButton = newButton;
    }
}