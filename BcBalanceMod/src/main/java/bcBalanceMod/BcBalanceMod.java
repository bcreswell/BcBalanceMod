package bcBalanceMod;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.Finisher;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.relics.MonkeyPaw;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import bcBalanceMod.util.IDCheckDontTouchPls;
import bcBalanceMod.util.TextureLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "theDefault" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 4 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theDefault:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theDefault", and change to "yourmodname" rather than "thedefault".
// You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories, and press alt+c to make the replace case sensitive (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class BcBalanceMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber
{
    public static final Logger logger = LogManager.getLogger(BcBalanceMod.class.getName());
    private static String modID;
    
    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties bcBalanceModSettings = new Properties();
    static final String settingsFile = "bcBalanceModSettings";
    static final String alwaysPandorasSetting = "alwaysPandoras";
    public static boolean alwaysPandoras = false; // The boolean we'll be setting on/off (true/false)
    
    //This is for the in-game mod settings panel.
    private static final String MODNAME = "BC Balance Mod";
    private static final String AUTHOR = "BC";
    private static final String DESCRIPTION = "Lots of small changes to existing cards and relics. New cards for the original 4 characters, new relics, new potions. Extensive changes mean there are many mods that this mod isn't compatible with.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "bcBalanceModResources/images/Badge.png";
    
    public BcBalanceMod()
    {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
        setModID("bcBalanceMod");
        
        logger.info("Done subscribing");
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        bcBalanceModSettings.setProperty(alwaysPandorasSetting, "FALSE"); // This is the default setting. It's actually set...
        try
        {
            SpireConfig config = new SpireConfig("bcBalanceMod", settingsFile, bcBalanceModSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            alwaysPandoras = config.getBool(alwaysPandorasSetting);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }
    
    public static String makeCardPath(String resourcePath)
    {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath)
    {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath)
    {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath)
    {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath)
    {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath)
    {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID)
    { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = BcBalanceMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID))
        { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        }
        else if (ID.equals(EXCEPTION_STRINGS.DEVID))
        { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        }
        else
        { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID()
    { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck()
    { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = BcBalanceMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = BcBalanceMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID))
        { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID()))
            { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists())
            { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    public static void initialize()
    {
        logger.info("========================= Initializing BC Balance Mod. =========================");
        BcBalanceMod defaultmod = new BcBalanceMod();
        logger.info("========================= Finished initializing BC Balance Mod =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters()
    {
//        logger.info("Beginning to edit characters. " + "Add " + TheDefault.Enums.THE_DEFAULT.toString());
//
//        BaseMod.addCharacter(new TheDefault("the Default", TheDefault.Enums.THE_DEFAULT),
//                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDefault.Enums.THE_DEFAULT);
        
        receiveEditPotions();
//        logger.info("Added " + TheDefault.Enums.THE_DEFAULT.toString());
    }
    
    @Override
    public void receivePostInitialize()
    {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Neow's Boss Relic Swap is always Pandora's Box.", 350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                alwaysPandoras, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) ->
                {
                }, // thing??????? idk
                (button) ->
                { // The actual button:
                    
                    alwaysPandoras = button.enabled; // The boolean true/false will be whether the button is enabled or not
                    try
                    {
                        // And based on that boolean, set the settings and save them
                        SpireConfig config = new SpireConfig("bcBalanceMod", settingsFile, bcBalanceModSettings);
                        config.setBool(alwaysPandorasSetting, alwaysPandoras);
                        config.save();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        BcUtility.populateLocalizationPlaceholders();
        
        //region removing relics
        //replacing these relics with new versions so it's not necessary to patch all the places where they hardcoded it into the system
        AbstractRelic oldEctoplasm = RelicLibrary.getRelic(Ectoplasm.ID);
        BaseMod.removeRelic(oldEctoplasm);
        
        AbstractRelic oldSozu = RelicLibrary.getRelic(Sozu.ID);
        BaseMod.removeRelic(oldSozu);
        
        AbstractRelic oldHoveringKite = RelicLibrary.getRelic(HoveringKite.ID);
        BaseMod.removeRelic(oldHoveringKite);
        
        AbstractRelic oldMelange = RelicLibrary.getRelic(Melange.ID);
        BaseMod.removeRelic(oldMelange);
        
        AbstractRelic oldDreamCatcher = RelicLibrary.getRelic(DreamCatcher.ID);
        BaseMod.removeRelic(oldDreamCatcher);
        
        AbstractRelic oldSundial = RelicLibrary.getRelic(Sundial.ID);
        BaseMod.removeRelic(oldSundial);
        
        AbstractRelic oldAbacus = RelicLibrary.getRelic(Abacus.ID);
        BaseMod.removeRelic(oldAbacus);
        //endregion
        
        //ironclad
        BcUtility.removeCardFromMasterLibrary(Intimidate.ID);
        BcUtility.removeCardFromMasterLibrary(Rupture.ID);
        
        //silent
        BcUtility.removeCardFromMasterLibrary(Finisher.ID);
        
        //defect
        BcUtility.removeCardFromMasterLibrary(Melter.ID);
        BcUtility.removeCardFromMasterLibrary(DoubleEnergy.ID);
        BcUtility.removeCardFromMasterLibrary(Aggregate.ID);
        BcUtility.removeCardFromMasterLibrary(AutoShields.ID);
        
        //watcher
        BcUtility.removeCardFromMasterLibrary(Worship.ID);
        BcUtility.removeCardFromMasterLibrary(Wish.ID);
        //BcUtility.removeCardFromMasterLibrary(Blasphemy.ID);
        BcUtility.removeCardFromMasterLibrary(Pray.ID);
        BcUtility.removeCardFromMasterLibrary(Indignation.ID);
        BcUtility.removeCardFromMasterLibrary(WaveOfTheHand.ID);
        BcUtility.removeCardFromMasterLibrary(Foresight.ID);
        
        for (String character : UnlockTracker.lockedCharacters)
        {
            UnlockTracker.hardUnlockOverride(character);
        }
        
        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events
        
        //BaseMod.addEvent(FreeYourMind.ID, FreeYourMind.class, TheCity.ID);
        
        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID
        
        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default
        
        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
//        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class) // for this specific event
//            .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
//            .playerClass(TheDefault.Enums.THE_DEFAULT) // Character specific event
//            .create();
//
//        // Add the event
//        BaseMod.addEvent(eventParams);
        
        // =============== /EVENTS/ =================
        
        BcUtility.logAllCards();
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions()
    {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        BaseMod.addPotion(RetainPotion.class, RetainPotion.LiquidColor, RetainPotion.HybridColor, RetainPotion.SpotsColor, RetainPotion.POTION_ID);
        BaseMod.addPotion(FelixFelicisPotion.class, FelixFelicisPotion.LiquidColor, FelixFelicisPotion.HybridColor, null, FelixFelicisPotion.POTION_ID);
        BaseMod.addPotion(BufferPotion.class, BufferPotion.LiquidColor, BufferPotion.HybridColor, null, BufferPotion.POTION_ID);
        BaseMod.addPotion(MultiBlockPotion.class, MultiBlockPotion.LiquidColor, MultiBlockPotion.HybridColor, null, MultiBlockPotion.POTION_ID);
        
        logger.info("Done editing potions");
    }
    
    @Override
    public void receiveEditRelics()
    {
        logger.info("Adding relics");
        
        // Take a look at https://github.com/daviscook477/BaseMod/wiki/AutoAdd
        // as well as
        // https://github.com/kiooeht/Bard/blob/e023c4089cc347c60331c78c6415f489d19b6eb9/src/main/java/com/evacipated/cardcrawl/mod/bard/BardMod.java#L319
        // for reference as to how to turn this into an "Auto-Add" rather than having to list every relic individually.
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        //BaseMod.addRelicToCustomPool(new PlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);
        //BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheDefault.Enums.COLOR_GRAY);
        //BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheDefault.Enums.COLOR_GRAY);
        
        BaseMod.addRelic(new BcBalancingScales(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BcBalancingScales.ID);
        
        BaseMod.addRelic(new RotaryMechanism(), RelicType.BLUE);
        UnlockTracker.markRelicAsSeen(RotaryMechanism.ID);
        
        BaseMod.addRelic(new PersonalDigitalAssistant(), RelicType.BLUE);
        UnlockTracker.markRelicAsSeen(PersonalDigitalAssistant.ID);
        
        BaseMod.addRelic(new GoldenFeather(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(GoldenFeather.ID);
        
        BaseMod.addRelic(new EmptyVessel(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(EmptyVessel.ID);
        
        BaseMod.addRelic(new UnbreakableHeart(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(UnbreakableHeart.ID);
        
        BaseMod.addRelic(new MonkeyPaw(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(MonkeyPaw.ID);
        
        BaseMod.addRelic(new PowerGlove(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(PowerGlove.ID);
        
        //the following relics replace base game versions to work around their effects being hardcoded into the system
        BaseMod.addRelic(new BcEctoplasm(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BcEctoplasm.ID);
        
        BaseMod.addRelic(new BcHoveringKite(), RelicType.GREEN);
        UnlockTracker.markRelicAsSeen(BcHoveringKite.ID);
        
        BaseMod.addRelic(new BcSozu(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BcSozu.ID);
        
        BaseMod.addRelic(new BcMelange(), RelicType.PURPLE);
        UnlockTracker.markRelicAsSeen(BcMelange.ID);
        
        BaseMod.addRelic(new BcDreamCatcher(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BcDreamCatcher.ID);
        
        BaseMod.addRelic(new BcSundial(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BcSundial.ID);
        
        BaseMod.addRelic(new BcAbacus(), RelicType.SHARED);
        UnlockTracker.markRelicAsSeen(BcAbacus.ID);
        
        logger.info("Done adding relics!");
    }
    /*
    public static void removeRelic(String relicId)
    {
        AbstractRelic relic = getRelic(relicId);
        switch (relic.)
        {
            case SHARED:
                HashMap<String, AbstractRelic> sharedRelics = (HashMap) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "sharedRelics");
                if (sharedRelics.containsKey(relicId))
                {
                    sharedRelics.remove(relic.relicId);
                    --RelicLibrary.totalRelicCount;
                    removeRelicFromTierList(relic);
                }
                break;
            case RED:
                HashMap<String, AbstractRelic> redRelics = (HashMap) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "redRelics");
                if (redRelics.containsKey(relic.relicId))
                {
                    redRelics.remove(relic.relicId);
                    --RelicLibrary.totalRelicCount;
                    removeRelicFromTierList(relic);
                }
                break;
            case GREEN:
                HashMap<String, AbstractRelic> greenRelics = (HashMap) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "greenRelics");
                if (greenRelics.containsKey(relic.relicId))
                {
                    greenRelics.remove(relic.relicId);
                    --RelicLibrary.totalRelicCount;
                    removeRelicFromTierList(relic);
                }
                break;
            case BLUE:
                HashMap<String, AbstractRelic> blueRelics = (HashMap) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "blueRelics");
                if (blueRelics.containsKey(relic.relicId))
                {
                    blueRelics.remove(relic.relicId);
                    --RelicLibrary.totalRelicCount;
                    removeRelicFromTierList(relic);
                }
                break;
            case PURPLE:
                HashMap<String, AbstractRelic> purpleRelics = (HashMap) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "purpleRelics");
                if (purpleRelics.containsKey(relic.relicId))
                {
                    purpleRelics.remove(relic.relicId);
                    --RelicLibrary.totalRelicCount;
                    removeRelicFromTierList(relic);
                }
                break;
            default:
                logger.info("tried to remove relic of unsupported type: " + relic + " " + type);
        }
    }
    */
    
    private static void removeRelicFromTierList(AbstractRelic relic)
    {
        switch (relic.tier)
        {
            case STARTER:
                RelicLibrary.starterList.remove(relic);
                break;
            case COMMON:
                RelicLibrary.commonList.remove(relic);
                break;
            case UNCOMMON:
                RelicLibrary.uncommonList.remove(relic);
                break;
            case RARE:
                RelicLibrary.rareList.remove(relic);
                break;
            case SHOP:
                RelicLibrary.shopList.remove(relic);
                break;
            case SPECIAL:
                RelicLibrary.specialList.remove(relic);
                break;
            case BOSS:
                RelicLibrary.bossList.remove(relic);
                break;
            case DEPRECATED:
                logger.info(relic.relicId + " is deprecated.");
                break;
            default:
                logger.info(relic.relicId + "is undefined tier.");
        }
    }
    
    @Override
    public void receiveEditCards()
    {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        //BaseMod.addDynamicVariable(new DefaultCustomVariable());
        //BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        
        new AutoAdd("BcBalanceMod").packageFilter(AbstractCard.class).setDefaultSeen(true).cards();
        
        logger.info("Done adding cards!");
    }
    
    @Override
    public void receiveEditStrings()
    {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class, getModID() + "Resources/localization/eng/BcBalanceMod-Orb-Strings.json");
        
        logger.info("Done editing strings");
    }
    
    @Override
    public void receiveEditKeywords()
    {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/BcBalanceMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null)
        {
            for (Keyword keyword : keywords)
            {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText)
    {
        return getModID() + ":" + idText;
    }
}
