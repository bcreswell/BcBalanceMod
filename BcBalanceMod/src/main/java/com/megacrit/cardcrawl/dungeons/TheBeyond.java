package com.megacrit.cardcrawl.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheBeyond extends AbstractDungeon {
    private static final Logger logger = LogManager.getLogger(TheBeyond.class.getName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public static final String NAME;
    public static final String ID = "TheBeyond";
    
    public TheBeyond(AbstractPlayer p, ArrayList<String> theList) {
        super(NAME, "TheBeyond", p, theList);
        if (scene != null) {
            scene.dispose();
        }
        
        scene = new TheBeyondScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        this.initializeLevelSpecificChances();
        mapRng = new Random(Settings.seed + (long)(AbstractDungeon.actNum * 200));
        generateMap();
        CardCrawlGame.music.changeBGM(id);
    }
    
    public TheBeyond(AbstractPlayer p, SaveFile saveFile) {
        super(NAME, p, saveFile);
        CardCrawlGame.dungeon = this;
        if (scene != null) {
            scene.dispose();
        }
        
        scene = new TheBeyondScene();
        fadeColor = Color.valueOf("140a1eff");
        sourceFadeColor = Color.valueOf("140a1eff");
        this.initializeLevelSpecificChances();
        miscRng = new Random(Settings.seed + (long)saveFile.floor_num);
        CardCrawlGame.music.changeBGM(id);
        mapRng = new Random(Settings.seed + (long)(saveFile.act_num * 200));
        generateMap();
        firstRoomChosen = true;
        this.populatePathTaken(saveFile);
    }
    
    protected void initializeLevelSpecificChances() {
        shopRoomChance = 0.05F;
        restRoomChance = 0.12F;
        treasureRoomChance = 0.0F;
        eventRoomChance = 0.22F;
        eliteRoomChance = 0.08F;
        smallChestChance = 50;
        mediumChestChance = 33;
        largeChestChance = 17;
        commonRelicChance = 50;
        uncommonRelicChance = 33;
        rareRelicChance = 17;
        colorlessRareChance = 0.3F;
//        if (AbstractDungeon.ascensionLevel >= 12) {
//            cardUpgradedChance = 0.25F;
//        } else {
//            cardUpgradedChance = 0.5F;
//        }
        cardUpgradedChance = 0.3F;
    }
    
    protected void generateMonsters() {
        this.generateWeakEnemies(2);
        this.generateStrongEnemies(12);
        this.generateElites(10);
    }
    
    protected void generateWeakEnemies(int count) {
        ArrayList<MonsterInfo> monsters = new ArrayList();
        monsters.add(new MonsterInfo("3 Darklings", 2.0F));
        monsters.add(new MonsterInfo("Orb Walker", 2.0F));
        monsters.add(new MonsterInfo("3 Shapes", 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        this.populateMonsterList(monsters, count, false);
    }
    
    protected void generateStrongEnemies(int count) {
        ArrayList<MonsterInfo> monsters = new ArrayList();
        monsters.add(new MonsterInfo("Spire Growth", 1.0F));
        monsters.add(new MonsterInfo("Transient", 1.0F));
        monsters.add(new MonsterInfo("4 Shapes", 1.0F));
        monsters.add(new MonsterInfo("Maw", 1.0F));
        monsters.add(new MonsterInfo("Sphere and 2 Shapes", 1.0F));
        monsters.add(new MonsterInfo("Jaw Worm Horde", 1.0F));
        monsters.add(new MonsterInfo("3 Darklings", 1.0F));
        monsters.add(new MonsterInfo("Writhing Mass", 1.0F));
        MonsterInfo.normalizeWeights(monsters);
        this.populateFirstStrongEnemy(monsters, this.generateExclusions());
        this.populateMonsterList(monsters, count, false);
    }
    
    protected void generateElites(int count) {
        ArrayList<MonsterInfo> monsters = new ArrayList();
        monsters.add(new MonsterInfo("Giant Head", 2.0F));
        monsters.add(new MonsterInfo("Nemesis", 2.0F));
        monsters.add(new MonsterInfo("Reptomancer", 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        this.populateMonsterList(monsters, count, true);
    }
    
    protected ArrayList<String> generateExclusions() {
        ArrayList<String> retVal = new ArrayList();
        String var2 = (String)monsterList.get(monsterList.size() - 1);
        byte var3 = -1;
        switch(var2.hashCode()) {
            case -500373089:
                if (var2.equals("3 Shapes")) {
                    var3 = 2;
                }
                break;
            case 1014856122:
                if (var2.equals("3 Darklings")) {
                    var3 = 0;
                }
                break;
            case 1679632599:
                if (var2.equals("Orb Walker")) {
                    var3 = 1;
                }
        }
        
        switch(var3) {
            case 0:
                retVal.add("3 Darklings");
                break;
            case 1:
                retVal.add("Orb Walker");
                break;
            case 2:
                retVal.add("4 Shapes");
        }
        
        return retVal;
    }
    
    protected void initializeBoss() {
        bossList.clear();
        if (Settings.isDailyRun) {
            bossList.add("Awakened One");
            bossList.add("Time Eater");
            bossList.add("Donu and Deca");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        } else if (!UnlockTracker.isBossSeen("CROW")) {
            bossList.add("Awakened One");
        } else if (!UnlockTracker.isBossSeen("DONUT")) {
            bossList.add("Donu and Deca");
        } else if (!UnlockTracker.isBossSeen("WIZARD")) {
            bossList.add("Time Eater");
        } else {
            bossList.add("Awakened One");
            bossList.add("Time Eater");
            bossList.add("Donu and Deca");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
        
        if (bossList.size() == 1) {
            bossList.add(bossList.get(0));
        } else if (bossList.isEmpty()) {
            logger.warn("Boss list was empty. How?");
            bossList.add("Awakened One");
            bossList.add("Time Eater");
            bossList.add("Donu and Deca");
            Collections.shuffle(bossList, new java.util.Random(monsterRng.randomLong()));
        }
        
    }
    
    protected void initializeEventList() {
        eventList.add("Falling");
        eventList.add("MindBloom");
        eventList.add("The Moai Head");
        eventList.add("Mysterious Sphere");
        eventList.add("SensoryStone");
        eventList.add("Tomb of Lord Red Mask");
        eventList.add("Winding Halls");
    }
    
    protected void initializeEventImg() {
        if (eventBackgroundImg != null) {
            eventBackgroundImg.dispose();
            eventBackgroundImg = null;
        }
        
        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");
    }
    
    protected void initializeShrineList() {
        shrineList.add("Match and Keep!");
        shrineList.add("Wheel of Change");
        shrineList.add("Golden Shrine");
        shrineList.add("Transmorgrifier");
        shrineList.add("Purifier");
        shrineList.add("Upgrade Shrine");
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("TheBeyond");
        TEXT = uiStrings.TEXT;
        NAME = TEXT[0];
    }
}
