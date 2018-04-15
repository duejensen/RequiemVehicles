package org.requiem.mods.vehicles.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.requiem.mods.vehicles.Vehicles;

import java.io.IOException;

public class Knarrs {

    public static int addKnarrs(String model, String name) {
        Vehicles.debug("Initiating Requiem Knarr " + model);
        try {
            return createItem(model, name);
        } catch (Exception e) {
            Vehicles.debug("Initialization of knarrs failed: " + e.toString());
        }
        return 0;
    }
//        createItemTemplate(542, "knarr", "knarrs",
// "almost full", "somewhat occupied", "half-full", "emptyish",
// "A ship with a clinker-built hull assembled with iron rivets, and one mast with a square yard sail. In insufficient wind it is rowed with oars. The side rudder is on the starboard side.",
// new short[] { 60, 108, 1, 31, 21, 51, 56, 52, 44, 92, 117, 47, 48, 54, 157, 180, 160, 249 },
// (short)60,
// (short)41,
// 0,
// 9072000L,
// 400, 800, 1500,
// -10,
// MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
// "model.structure.boat.knarr.",
// 60.0f,
// 2000000,
// (byte)14,
// 10000,
// true)
// .setContainerSize(398, 385, 495)
// .setSecondryItem("sail");
    private static int createItem(String model, String name) throws IOException {
        Vehicles.debug("id :  org.requiem.boat.knarr." + name);
        ItemTemplateBuilder builder = new ItemTemplateBuilder("org.requiem.boat.knarr." + name);
        builder.name(name + " knarr", name + " knarrs", "Knarr is the Old Norse term for a type of ship built for long sea voyages and used during the Viking expansion. The knarr was a cargo ship; the hull was wider, deeper and shorter than a longship, and could take more cargo and be operated by smaller crews. They were built with a length of about 16 m (54 ft), a beam of 5 m (15 ft), and a hull capable of carrying up to 24 tons.");
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
        builder.imageNumber((short) 60);
        builder.behaviourType(BehaviourList.vehicleBehaviour);
        builder.combatDamage(0);
        builder.decayTime(9072000L);
        builder.dimensions(400, 800, 1500);
        builder.primarySkill(-10);
        builder.bodySpaces(MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY);
        builder.modelName(model + ".");
        builder.difficulty(60.0F);
        builder.weightGrams(2000000);
        builder.material(ItemMaterials.MATERIAL_WOOD_BIRCH);
        builder.value(10000);
        builder.isTraded(true);
        builder.containerSize(398, 385, 495);
        //builder.setSecondaryItem("sail");
        ItemTemplate result = builder.build();
        

        return result.getTemplateId();
    }

    public static void createCreationEntry(int templateId) {
        final AdvancedCreationEntry lKnarr = CreationEntryCreator.createAdvancedEntry
                (SkillList.SHIPBUILDING, ItemList.keelPart, ItemList.keelPart, templateId,
                false, false, 0.0f, true, true, CreationCategories.SHIPS);
        lKnarr.addRequirement(new CreationRequirement(1, ItemList.keelPart, 3, true));
        lKnarr.addRequirement(new CreationRequirement(2, ItemList.stern, 1, true));
        lKnarr.addRequirement(new CreationRequirement(3, ItemList.hullPlank, 400, true));
        lKnarr.addRequirement(new CreationRequirement(4, ItemList.tenon, 200, true));
        lKnarr.addRequirement(new CreationRequirement(5, ItemList.pegWood, 400, true));
        lKnarr.addRequirement(new CreationRequirement(6, ItemList.metalRivet, 200, true));
        lKnarr.addRequirement(new CreationRequirement(7, ItemList.rigSquareYard, 1, true));
        lKnarr.addRequirement(new CreationRequirement(8, ItemList.ropeThick, 8, true));
        lKnarr.addRequirement(new CreationRequirement(9, ItemList.ropeMooring, 4, true));
        lKnarr.addRequirement(new CreationRequirement(10, ItemList.rudder, 1, true));
        lKnarr.addRequirement(new CreationRequirement(11, ItemList.deckBoard, 80, true));
        lKnarr.addRequirement(new CreationRequirement(12, ItemList.tar, 100, true));
        lKnarr.addRequirement(new CreationRequirement(13, ItemList.oar, 10, true));
        lKnarr.addRequirement(new CreationRequirement(14, ItemList.belayingPin, 10, true));
        lKnarr.setIsEpicBuildMissionTarget(false);
    }

}
