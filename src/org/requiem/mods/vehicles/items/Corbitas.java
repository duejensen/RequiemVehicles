package org.requiem.mods.vehicles.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.requiem.mods.vehicles.Vehicles;

import java.io.IOException;

public class Corbitas {

    public static int addCorbitas(String model, String name) {
        Vehicles.debug("Initiating Requiem Corbita " + model);
        try {
            return createItem(model, name);
        } catch (Exception e) {
            Vehicles.debug("Initialization of corbitas failed: " + e.toString());
        }
        return 0;
    }
//        createItemTemplate(541, "corbita", "corbitas", "almost full", "somewhat occupied", "half-full", "emptyish",
// "A ship with square sails, steered using two side rudders connected to each other.",
// new short[] { 60, 108, 1, 31, 21, 51, 56, 52, 44, 92, 117, 47, 48, 54, 157, 180, 160, 249 },
// (short)60,
// (short)41,
// 0,
// 9072000L,
// 210, 600, 810,
// -10,
// MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
// "model.structure.boat.corbita.",
// 40.0f,
// 1400000,
// (byte)14,
// 10000,
// true)
// .setContainerSize(210, 550, 740)
// .setSecondryItem("sail");
    private static int createItem(String model, String name) throws IOException {
        Vehicles.debug("id :  org.requiem.boat.corbita." + name);
        ItemTemplateBuilder builder = new ItemTemplateBuilder("org.requiem.boat.corbita." + name);
        builder.name(name + " corbita", name + " corbitas", "The merchant ship of imperial Rome, a large, full-bodied vessel massively built and capable of carrying as much as 400 tons of cargo. They set a single large square sail on a mast amidships, sometimes with two raffee topsails above the yard, and a small square sail on an artemon mast over the bows. It was steered with two deep steering oars, one on each quarter, which in strong winds and high seas required as many as four men on each oar to control the vessel.");
        builder.descriptions("almost full", "somewhat occupied", "half-full", "emptyish");
        builder.itemTypes(new short[]{
                ItemTypes.ITEM_TYPE_FLOATING,
                ItemTypes.ITEM_TYPE_NAMED,
                ItemTypes.ITEM_TYPE_HOLLOW,
                ItemTypes.ITEM_TYPE_NOTAKE,
                ItemTypes.ITEM_TYPE_WOOD,
                ItemTypes.ITEM_TYPE_TURNABLE,
                ItemTypes.ITEM_TYPE_DRAGGABLE,
                ItemTypes.ITEM_TYPE_DECORATION,
                ItemTypes.ITEM_TYPE_REPAIRABLE,
                ItemTypes.ITEM_TYPE_COLORABLE,
                ItemTypes.ITEM_TYPE_VEHICLE,
                ItemTypes.ITEM_TYPE_LOCKABLE,
                ItemTypes.ITEM_TYPE_HASDATA,
                ItemTypes.ITEM_TYPE_NORENAME,
                ItemTypes.ITEM_TYPE_NOT_MISSION,
                ItemTypes.ITEM_TYPE_USES_SPECIFIED_CONTAINER_VOLUME,
                ItemTypes.ITEM_TYPE_NOWORKPARENT,
                ItemTypes.ITEM_TYPE_SUPPORTS_SECONDARY_COLOR
        });
        builder.imageNumber((short) 640);
        builder.behaviourType(BehaviourList.vehicleBehaviour);
        builder.combatDamage(0);
        builder.decayTime(9072000L);
        builder.dimensions(210, 600, 810);
        builder.primarySkill(-10);
        builder.bodySpaces(MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY);
        builder.modelName(model + ".");
        builder.difficulty(40.0F);
        builder.weightGrams(1400000);
        builder.material(ItemMaterials.MATERIAL_WOOD_BIRCH);
        builder.value(10000);
        builder.isTraded(true);
        builder.containerSize(210, 550, 740);
        //builder.setSecondaryItem("sail");
        ItemTemplate result = builder.build();
        createCreationEntry(result);

        return result.getTemplateId();
    }

    private static void createCreationEntry(ItemTemplate newCorbita) {
        final AdvancedCreationEntry lCorbita = CreationEntryCreator.createAdvancedEntry(
                SkillList.SHIPBUILDING, ItemList.keelPart, ItemList.keelPart, newCorbita.getTemplateId(),
                false, false, 0.0f, true, true, CreationCategories.SHIPS);
        lCorbita.addRequirement(new CreationRequirement(1, ItemList.keelPart, 2, true));
        lCorbita.addRequirement(new CreationRequirement(2, ItemList.stern, 1, true));
        lCorbita.addRequirement(new CreationRequirement(3, ItemList.hullPlank, 200, true));
        lCorbita.addRequirement(new CreationRequirement(4, ItemList.tenon, 200, true));
        lCorbita.addRequirement(new CreationRequirement(5, ItemList.pegWood, 400, true));
        lCorbita.addRequirement(new CreationRequirement(6, ItemList.rigSquare, 1, true));
        lCorbita.addRequirement(new CreationRequirement(7, ItemList.ropeThick, 8, true));
        lCorbita.addRequirement(new CreationRequirement(8, ItemList.ropeMooring, 4, true));
        lCorbita.addRequirement(new CreationRequirement(9, ItemList.rudder, 2, true));
        lCorbita.addRequirement(new CreationRequirement(10, ItemList.deckBoard, 40, true));
        lCorbita.addRequirement(new CreationRequirement(11, ItemList.tar, 50, true));
        lCorbita.addRequirement(new CreationRequirement(12, ItemList.oar, 2, true));
        lCorbita.addRequirement(new CreationRequirement(13, ItemList.belayingPin, 10, true));
        lCorbita.addRequirement(new CreationRequirement(14, ItemList.rigSpinnaker, 1, true));
        lCorbita.setIsEpicBuildMissionTarget(false);
    }

}
