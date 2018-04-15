package org.requiem.mods.vehicles.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.requiem.mods.vehicles.Vehicles;

import java.io.IOException;

public class Cogs {

    public static int addCogs(String model, String name) {
        Vehicles.debug("Initiating Kingdom Cogs " + model);
        try {
            return createItem(model, name);
        } catch (Exception e) {
            Vehicles.debug("Initialization of cogs failed: " + e.toString());
        }
        return 0;
    }
//        createItemTemplate(540, "cog", "cogs",
// "almost full", "somewhat occupied", "half-full", "emptyish",
// "A sturdy, one-masted merchant ship with a flat bottom.",
// new short[] { 60, 108, 1, 31, 21, 51, 56, 52, 44, 92, 117, 47, 48, 54, 157, 160, 249 },
// (short)60,
// (short)41,
// 0,
// 9072000L,
// 210, 400, 610,
// -10,
// MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
// "model.structure.boat.cog.",
// 50.0f,
// 800000,
// (byte)14,
// 10000,
// true)
// .setSecondryItem("sail");
    private static int createItem(String model, String name) throws IOException {
        Vehicles.debug("id :  org.requiem.boat.cog." + name);
        ItemTemplateBuilder builder = new ItemTemplateBuilder("org.requiem.boat.cog." + name);
        builder.name(name + " cog", name + " cogs", "A cog is a type of ship that first appeared in the 10th century, and was widely used from around the 12th century on. Cogs were clinker-built, generally of oak, which was an abundant timber in the Baltic region of Prussia. This vessel was fitted with a single mast and a square-rigged single sail. These vessels were mostly associated with seagoing trade in medieval Europe, especially the Hanseatic League, particularly in the Baltic Sea region. They ranged from about 15 to 25 meters (49 to 82 ft) in length with a beam of 5 to 8 meters (16 to 26 ft), and the largest cog ships could carry up to about 200 tons.");
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
        builder.dimensions(210, 400, 610);
        builder.primarySkill(-10);
        builder.bodySpaces(MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY);
        builder.modelName(model + ".");
        builder.difficulty(50.0F);
        builder.weightGrams(800000);
        builder.material(ItemMaterials.MATERIAL_WOOD_BIRCH);
        builder.value(10000);
        builder.isTraded(true);
        //builder.setSecondaryItem("sail");
        ItemTemplate result = builder.build();
    
        return result.getTemplateId();
    }

    public static void createCreationEntry(int templateId) {
        final AdvancedCreationEntry lCog = CreationEntryCreator.createAdvancedEntry(
                SkillList.SHIPBUILDING, ItemList.keelPart, ItemList.keelPart, templateId,
                false, false, 0.0f, true, true, CreationCategories.SHIPS);
        lCog.addRequirement(new CreationRequirement(1, ItemList.keelPart, 2, true));
        lCog.addRequirement(new CreationRequirement(2, ItemList.stern, 1, true));
        lCog.addRequirement(new CreationRequirement(3, ItemList.hullPlank, 300, true));
        lCog.addRequirement(new CreationRequirement(4, ItemList.tenon, 200, true));
        lCog.addRequirement(new CreationRequirement(5, ItemList.pegWood, 400, true));
        lCog.addRequirement(new CreationRequirement(6, ItemList.rigSquareLarge, 1, true));
        lCog.addRequirement(new CreationRequirement(7, ItemList.ropeThick, 8, true));
        lCog.addRequirement(new CreationRequirement(8, ItemList.ropeMooring, 4, true));
        lCog.addRequirement(new CreationRequirement(9, ItemList.rudder, 1, true));
        lCog.addRequirement(new CreationRequirement(10, ItemList.deckBoard, 60, true));
        lCog.addRequirement(new CreationRequirement(11, ItemList.tar, 50, true));
        lCog.addRequirement(new CreationRequirement(12, ItemList.oar, 2, true));
        lCog.addRequirement(new CreationRequirement(13, ItemList.belayingPin, 10, true));
        lCog.setIsEpicBuildMissionTarget(false);
    }
}
