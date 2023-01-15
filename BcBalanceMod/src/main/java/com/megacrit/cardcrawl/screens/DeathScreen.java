package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.ReturnToMenuButton;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.DefectUnlock;
import com.megacrit.cardcrawl.unlock.misc.TheSilentUnlock;
import com.megacrit.cardcrawl.unlock.misc.WatcherUnlock;
import com.megacrit.cardcrawl.vfx.AscensionLevelUpTextEffect;
import com.megacrit.cardcrawl.vfx.AscensionUnlockedTextEffect;
import com.megacrit.cardcrawl.vfx.DeathScreenFloatyEffect;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeathScreen extends GameOverScreen {
    private static final Logger logger = LogManager.getLogger(DeathScreen.class.getName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private MonsterGroup monsters;
    private String deathText;
    private ArrayList<DeathScreenFloatyEffect> particles = new ArrayList();
    private static final float NUM_PARTICLES = 50.0F;
    private float deathAnimWaitTimer = 1.0F;
    private static final float DEATH_TEXT_TIME = 5.0F;
    private float deathTextTimer = 5.0F;
    private Color defeatTextColor;
    private Color deathTextColor;
    private static final float DEATH_TEXT_Y;
    private boolean defectUnlockedThisRun;
    
    public DeathScreen(MonsterGroup m) {
        this.defeatTextColor = Color.WHITE.cpy();
        this.deathTextColor = Settings.BLUE_TEXT_COLOR.cpy();
        this.defectUnlockedThisRun = false;
        this.playtime = (long)CardCrawlGame.playtime;
        if (this.playtime < 0L) {
            this.playtime = 0L;
        }
        
        AbstractDungeon.getCurrRoom().clearEvent();
        resetScoreChecks();
        AbstractDungeon.is_victory = false;
        Iterator var2 = AbstractDungeon.player.hand.group.iterator();
        
        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();
            c.unhover();
        }
        
        if (AbstractDungeon.player.stance != null) {
            AbstractDungeon.player.stance.stopIdleSfx();
        }
        
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.DEATH;
        AbstractDungeon.overlayMenu.showBlackScreen(1.0F);
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        AbstractDungeon.isScreenUp = true;
        this.deathText = this.getDeathText();
        this.monsters = m;
        logger.info("PLAYTIME: " + this.playtime);
        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
            ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        }
        
        isVictory = AbstractDungeon.getCurrRoom() instanceof VictoryRoom;
        if (!isVictory) {
            PublisherIntegration pubInteg = CardCrawlGame.publisherIntegration;
            String winStreakStatId;
            if (Settings.isBeta) {
                winStreakStatId = AbstractDungeon.player.getWinStreakKey() + "_BETA";
            } else {
                winStreakStatId = AbstractDungeon.player.getWinStreakKey();
            }
            
            pubInteg.setStat(winStreakStatId, 0);
            logger.info("WIN STREAK  " + pubInteg.getStat(winStreakStatId));
        }
        
        this.showingStats = false;
        this.returnButton = new ReturnToMenuButton();
        this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[0]);
        if (AbstractDungeon.getCurrRoom() instanceof VictoryRoom) {
            AbstractDungeon.dynamicBanner.appear(TEXT[1]);
        } else {
            AbstractDungeon.dynamicBanner.appear(this.getDeathBannerText());
        }
        
        if (Settings.isStandardRun()) {
            if (AbstractDungeon.floorNum >= 16) {
                logger.info("Neow available");
                CardCrawlGame.playerPref.putInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 1);
            } else {
                logger.info("No Neow for you");
                CardCrawlGame.playerPref.putInteger(AbstractDungeon.player.chosenClass.name() + "_SPIRITS", 0);
                AbstractDungeon.bossCount = 0;
            }
        }
        
        CardCrawlGame.music.dispose();
        CardCrawlGame.sound.play("DEATH_STINGER", true);
        String bgmKey = null;
        switch(MathUtils.random(0, 3)) {
            case 0:
                bgmKey = "STS_DeathStinger_1_v3_MUSIC.ogg";
                break;
            case 1:
                bgmKey = "STS_DeathStinger_2_v3_MUSIC.ogg";
                break;
            case 2:
                bgmKey = "STS_DeathStinger_3_v3_MUSIC.ogg";
                break;
            case 3:
                bgmKey = "STS_DeathStinger_4_v3_MUSIC.ogg";
        }
        
        CardCrawlGame.music.playTempBgmInstantly(bgmKey, false);
        if (isVictory) {
            UnlockTracker.unlockAchievement(AbstractDungeon.player.getAchievementKey());
            this.submitVictoryMetrics();
            if (this.playtime != 0L) {
                StatsScreen.updateVictoryTime(this.playtime);
            }
            
            StatsScreen.incrementVictory(AbstractDungeon.player.getCharStat());
            if (AbstractDungeon.ascensionLevel == 10 && !Settings.isTrial) {
                UnlockTracker.unlockAchievement("ASCEND_10");
            } else if (AbstractDungeon.ascensionLevel == 20 && !Settings.isTrial) {
                UnlockTracker.unlockAchievement("ASCEND_20");
            }
        } else {
            if (AbstractDungeon.ascensionLevel == 20 && AbstractDungeon.actNum == 4) {
                UnlockTracker.unlockAchievement("ASCEND_20");
            }
            
            this.submitDefeatMetrics(m);
            StatsScreen.incrementDeath(AbstractDungeon.player.getCharStat());
        }
        
        if (Settings.isStandardRun() && AbstractDungeon.actNum > 3) {
            StatsScreen.incrementVictoryIfZero(AbstractDungeon.player.getCharStat());
        }
        
        this.defeatTextColor.a = 0.0F;
        this.deathTextColor.a = 0.0F;
        if (this.playtime != 0L) {
            StatsScreen.incrementPlayTime(this.playtime);
        }
        
        if (Settings.isStandardRun()) {
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        } else if (Settings.isDailyRun) {
            StatsScreen.updateHighestDailyScore(AbstractDungeon.floorNum);
        }
        
        if (SaveHelper.shouldDeleteSave()) {
            SaveAndContinue.deleteSave(AbstractDungeon.player);
        }
        
        this.calculateUnlockProgress();
        if (!Settings.isEndless) {
            this.uploadToSteamLeaderboards();
        }
        
        this.createGameOverStats();
        CardCrawlGame.playerPref.flush();
    }
    
    private void createGameOverStats() {
        this.stats.clear();
        this.stats.add(new GameOverStat(TEXT[2] + " (" + AbstractDungeon.floorNum + ")", (String)null, Integer.toString(floorPoints)));
        this.stats.add(new GameOverStat(TEXT[43] + " (" + CardCrawlGame.monstersSlain + ")", (String)null, Integer.toString(monsterPoints)));
        this.stats.add(new GameOverStat(EXORDIUM_ELITE.NAME + " (" + CardCrawlGame.elites1Slain + ")", (String)null, Integer.toString(elite1Points)));
        if (Settings.isEndless) {
            if (CardCrawlGame.elites2Slain > 0) {
                this.stats.add(new GameOverStat(CITY_ELITE.NAME + " (" + CardCrawlGame.elites2Slain + ")", (String)null, Integer.toString(elite2Points)));
            }
        } else if (CardCrawlGame.dungeon instanceof TheCity || CardCrawlGame.dungeon instanceof TheBeyond || CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(CITY_ELITE.NAME + " (" + CardCrawlGame.elites2Slain + ")", (String)null, Integer.toString(elite2Points)));
        }
        
        if (Settings.isEndless) {
            if (CardCrawlGame.elites3Slain > 0) {
                this.stats.add(new GameOverStat(BEYOND_ELITE.NAME + " (" + CardCrawlGame.elites3Slain + ")", (String)null, Integer.toString(elite3Points)));
            }
        } else if (CardCrawlGame.dungeon instanceof TheBeyond || CardCrawlGame.dungeon instanceof TheEnding) {
            this.stats.add(new GameOverStat(BEYOND_ELITE.NAME + " (" + CardCrawlGame.elites3Slain + ")", (String)null, Integer.toString(elite3Points)));
        }
        
        this.stats.add(new GameOverStat(BOSSES_SLAIN.NAME + " (" + AbstractDungeon.bossCount + ")", (String)null, Integer.toString(bossPoints)));
        if (IS_POOPY) {
            this.stats.add(new GameOverStat(POOPY.NAME, POOPY.DESCRIPTIONS[0], Integer.toString(-1)));
        }
        
        if (IS_SPEEDSTER) {
            this.stats.add(new GameOverStat(SPEEDSTER.NAME, SPEEDSTER.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (IS_LIGHT_SPEED) {
            this.stats.add(new GameOverStat(LIGHT_SPEED.NAME, LIGHT_SPEED.DESCRIPTIONS[0], Integer.toString(50)));
        }
        
        if (IS_HIGHLANDER) {
            this.stats.add(new GameOverStat(HIGHLANDER.NAME, HIGHLANDER.DESCRIPTIONS[0], Integer.toString(100)));
        }
        
        if (IS_SHINY) {
            this.stats.add(new GameOverStat(SHINY.NAME, SHINY.DESCRIPTIONS[0], Integer.toString(50)));
        }
        
        if (IS_I_LIKE_GOLD) {
            this.stats.add(new GameOverStat(I_LIKE_GOLD.NAME + " (" + CardCrawlGame.goldGained + ")", I_LIKE_GOLD.DESCRIPTIONS[0], Integer.toString(75)));
        } else if (IS_RAINING_MONEY) {
            this.stats.add(new GameOverStat(RAINING_MONEY.NAME + " (" + CardCrawlGame.goldGained + ")", RAINING_MONEY.DESCRIPTIONS[0], Integer.toString(50)));
        } else if (IS_MONEY_MONEY) {
            this.stats.add(new GameOverStat(MONEY_MONEY.NAME + " (" + CardCrawlGame.goldGained + ")", MONEY_MONEY.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (IS_MYSTERY_MACHINE) {
            this.stats.add(new GameOverStat(MYSTERY_MACHINE.NAME + " (" + CardCrawlGame.mysteryMachine + ")", MYSTERY_MACHINE.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (IS_FULL_SET > 0) {
            this.stats.add(new GameOverStat(COLLECTOR.NAME + " (" + IS_FULL_SET + ")", COLLECTOR.DESCRIPTIONS[0], Integer.toString(25 * IS_FULL_SET)));
        }
        
        if (IS_PAUPER) {
            this.stats.add(new GameOverStat(PAUPER.NAME, PAUPER.DESCRIPTIONS[0], Integer.toString(50)));
        }
        
        if (IS_LIBRARY) {
            this.stats.add(new GameOverStat(LIBRARIAN.NAME, LIBRARIAN.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (IS_ENCYCLOPEDIA) {
            this.stats.add(new GameOverStat(ENCYCLOPEDIAN.NAME, ENCYCLOPEDIAN.DESCRIPTIONS[0], Integer.toString(50)));
        }
        
        if (IS_STUFFED) {
            this.stats.add(new GameOverStat(STUFFED.NAME, STUFFED.DESCRIPTIONS[0], Integer.toString(50)));
        } else if (IS_WELL_FED) {
            this.stats.add(new GameOverStat(WELL_FED.NAME, WELL_FED.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (IS_CURSES) {
            this.stats.add(new GameOverStat(CURSES.NAME, CURSES.DESCRIPTIONS[0], Integer.toString(100)));
        }
        
        if (IS_ON_MY_OWN) {
            this.stats.add(new GameOverStat(ON_MY_OWN_TERMS.NAME, ON_MY_OWN_TERMS.DESCRIPTIONS[0], Integer.toString(50)));
        }
        
        if (CardCrawlGame.champion > 0) {
            this.stats.add(new GameOverStat(CHAMPION.NAME + " (" + CardCrawlGame.champion + ")", CHAMPION.DESCRIPTIONS[0], Integer.toString(25 * CardCrawlGame.champion)));
        }
        
        if (CardCrawlGame.perfect >= 3) {
            this.stats.add(new GameOverStat(BEYOND_PERFECT.NAME, BEYOND_PERFECT.DESCRIPTIONS[0], Integer.toString(200)));
        } else if (CardCrawlGame.perfect > 0) {
            this.stats.add(new GameOverStat(PERFECT.NAME + " (" + CardCrawlGame.perfect + ")", PERFECT.DESCRIPTIONS[0], Integer.toString(50 * CardCrawlGame.perfect)));
        }
        
        if (CardCrawlGame.overkill) {
            this.stats.add(new GameOverStat(OVERKILL.NAME, OVERKILL.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (CardCrawlGame.combo) {
            this.stats.add(new GameOverStat(COMBO.NAME, COMBO.DESCRIPTIONS[0], Integer.toString(25)));
        }
        
        if (AbstractDungeon.isAscensionMode) {
            this.stats.add(new GameOverStat(ASCENSION.NAME + " (" + AbstractDungeon.ascensionLevel + ")", ASCENSION.DESCRIPTIONS[0], Integer.toString(ascensionPoints)));
        }
        
        this.stats.add(new GameOverStat());
        this.stats.add(new GameOverStat(TEXT[6], (String)null, Integer.toString(this.score)));
    }
    
    private void submitDefeatMetrics(MonsterGroup m) {
        if (m != null && !m.areMonstersDead() && !m.areMonstersBasicallyDead()) {
            CardCrawlGame.metricData.addEncounterData();
        }
        
        Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(true, false, m);
        if (shouldUploadMetricData()) {
            metrics.setValues(true, false, m, Metrics.MetricRequestType.UPLOAD_METRICS);
            Thread t = new Thread(metrics);
            t.setName("Metrics");
            t.start();
        }
        
    }
    
    protected void submitVictoryMetrics() {
        Metrics metrics = new Metrics();
        metrics.gatherAllDataAndSave(false, false, (MonsterGroup)null);
        if (shouldUploadMetricData()) {
            metrics.setValues(false, false, (MonsterGroup)null, Metrics.MetricRequestType.UPLOAD_METRICS);
            Thread t = new Thread(metrics);
            t.start();
        }
        
        if (Settings.isStandardRun()) {
            StatsScreen.updateFurthestAscent(AbstractDungeon.floorNum);
        }
        
        if (SaveHelper.shouldDeleteSave()) {
            SaveAndContinue.deleteSave(AbstractDungeon.player);
        }
        
    }
    
    private String getDeathBannerText() {
        ArrayList<String> list = new ArrayList();
        list.add(TEXT[7]);
        list.add(TEXT[8]);
        list.add(TEXT[9]);
        list.add(TEXT[10]);
        list.add(TEXT[11]);
        list.add(TEXT[12]);
        list.add(TEXT[13]);
        list.add(TEXT[14]);
        return (String)list.get(MathUtils.random(list.size() - 1));
    }
    
    private String getDeathText() {
        ArrayList<String> list = new ArrayList();
        list.add(TEXT[15]);
        list.add(TEXT[16]);
        list.add(TEXT[17]);
        list.add(TEXT[18]);
        list.add(TEXT[19]);
        list.add(TEXT[20]);
        list.add(TEXT[21]);
        list.add(TEXT[22]);
        list.add(TEXT[23]);
        list.add(TEXT[24]);
        list.add(TEXT[25]);
        list.add(TEXT[26]);
        list.add(TEXT[27]);
        list.add(TEXT[28]);
        list.add(TEXT[29]);
        if (AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
            list.add("...");
        }
        
        return (String)list.get(MathUtils.random(list.size() - 1));
    }
    
    public void hide() {
        this.returnButton.hide();
        AbstractDungeon.dynamicBanner.hide();
    }
    
    public void reopen() {
        this.reopen(false);
    }
    
    public void reopen(boolean fromVictoryUnlock) {
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
        this.statsTimer = 0.5F;
        if (isVictory) {
            AbstractDungeon.dynamicBanner.appearInstantly(TEXT[1]);
        } else {
            AbstractDungeon.dynamicBanner.appearInstantly(TEXT[30]);
        }
        
        AbstractDungeon.overlayMenu.showBlackScreen(1.0F);
        if (fromVictoryUnlock) {
            this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[31]);
        } else if (!this.showingStats) {
            this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[32]);
        } else if (this.unlockBundle == null) {
            if (!isVictory) {
                if (!UnlockTracker.isCharacterLocked("The Silent") && (!UnlockTracker.isCharacterLocked("Defect") || AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.THE_SILENT) && !this.willWatcherUnlock()) {
                    this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[34]);
                } else {
                    this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                }
            } else {
                this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[35]);
            }
        } else {
            this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[36]);
        }
        
    }
    
    private boolean willWatcherUnlock() {
        if (!this.defectUnlockedThisRun && UnlockTracker.isCharacterLocked("Watcher")) {
            return !UnlockTracker.isCharacterLocked("Defect") && (UnlockTracker.isAchievementUnlocked("RUBY") || UnlockTracker.isAchievementUnlocked("EMERALD") || UnlockTracker.isAchievementUnlocked("SAPPHIRE"));
        } else {
            return false;
        }
    }
    
    public void update() {
        if (Settings.isDebug && InputHelper.justClickedRight) {
            UnlockTracker.resetUnlockProgress(AbstractDungeon.player.chosenClass);
        }
        
        this.updateControllerInput();
        this.returnButton.update();
        if (this.returnButton.hb.clicked || this.returnButton.show && CInputActionSet.select.isJustPressed()) {
            CInputActionSet.topPanel.unpress();
            if (Settings.isControllerMode) {
                Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            }
            
            this.returnButton.hb.clicked = false;
            if (!this.showingStats) {
                this.showingStats = true;
                this.statsTimer = 0.5F;
                logger.info("Clicked");
                this.returnButton = new ReturnToMenuButton();
                this.updateAscensionProgress();
                if (this.unlockBundle == null) {
                    if (!isVictory) {
                        if (!UnlockTracker.isCharacterLocked("The Silent") && (!UnlockTracker.isCharacterLocked("Defect") || AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.THE_SILENT) && !this.willWatcherUnlock()) {
                            this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[37]);
                        } else {
                            this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                        }
                    } else {
                        this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[39]);
                    }
                } else {
                    this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                }
            } else if (this.unlockBundle != null) {
                AbstractDungeon.gUnlockScreen.open(this.unlockBundle, false);
                AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.DEATH;
                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
                this.unlockBundle = null;
                if (UnlockTracker.isCharacterLocked("The Silent")) {
                    this.returnButton.appear((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT * 0.15F, TEXT[40]);
                } else {
                    this.returnButton.label = TEXT[37];
                }
            } else if (isVictory) {
                this.returnButton.hide();
                if (!AbstractDungeon.unlocks.isEmpty() && !Settings.isDemo) {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    if (Settings.isDailyRun) {
                        CardCrawlGame.startOver();
                    } else {
                        CardCrawlGame.playCreditsBgm = false;
                        CardCrawlGame.startOverButShowCredits();
                    }
                } else if (!Settings.isDemo && !Settings.isDailyRun) {
                    if (UnlockTracker.isCharacterLocked("The Silent")) {
                        AbstractDungeon.unlocks.add(new TheSilentUnlock());
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else if (UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
                        AbstractDungeon.unlocks.add(new DefectUnlock());
                        this.defectUnlockedThisRun = true;
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else if (this.willWatcherUnlock()) {
                        AbstractDungeon.unlocks.add(new WatcherUnlock());
                        AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                    } else {
                        CardCrawlGame.playCreditsBgm = false;
                        CardCrawlGame.startOverButShowCredits();
                    }
                } else {
                    CardCrawlGame.startOver();
                }
            } else {
                this.returnButton.hide();
                if (!AbstractDungeon.unlocks.isEmpty() && !Settings.isDemo && !Settings.isDailyRun && !Settings.isTrial) {
                    AbstractDungeon.unlocks.clear();
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    CardCrawlGame.playCreditsBgm = false;
                    CardCrawlGame.startOverButShowCredits();
                } else if (UnlockTracker.isCharacterLocked("The Silent")) {
                    AbstractDungeon.unlocks.add(new TheSilentUnlock());
                    AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                } else if (UnlockTracker.isCharacterLocked("Defect") && AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT) {
                    AbstractDungeon.unlocks.add(new DefectUnlock());
                    this.defectUnlockedThisRun = true;
                    AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                } else if (this.willWatcherUnlock()) {
                    AbstractDungeon.unlocks.add(new WatcherUnlock());
                    AbstractDungeon.unlockScreen.open((AbstractUnlock)AbstractDungeon.unlocks.remove(0));
                } else {
                    Settings.isTrial = false;
                    Settings.isDailyRun = false;
                    Settings.isEndless = false;
                    CardCrawlGame.trial = null;
                    CardCrawlGame.startOver();
                }
            }
        }
        
        this.updateStatsScreen();
        if (this.deathAnimWaitTimer != 0.0F) {
            this.deathAnimWaitTimer -= Gdx.graphics.getDeltaTime();
            if (this.deathAnimWaitTimer < 0.0F) {
                this.deathAnimWaitTimer = 0.0F;
                AbstractDungeon.player.playDeathAnimation();
            }
        } else {
            this.deathTextTimer -= Gdx.graphics.getDeltaTime();
            if (this.deathTextTimer < 0.0F) {
                this.deathTextTimer = 0.0F;
            }
            
            this.deathTextColor.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - this.deathTextTimer / 5.0F);
            this.defeatTextColor.a = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - this.deathTextTimer / 5.0F);
        }
        
        if (this.monsters != null) {
            this.monsters.update();
            this.monsters.updateAnimations();
        }
        
        if ((float)this.particles.size() < 50.0F) {
            this.particles.add(new DeathScreenFloatyEffect());
        }
        
    }
    
    private void updateControllerInput() {
        if (Settings.isControllerMode && !AbstractDungeon.topPanel.selectPotionMode && AbstractDungeon.topPanel.potionUi.isHidden && !AbstractDungeon.player.viewingRelics) {
            boolean anyHovered = false;
            int index = 0;
            if (this.stats != null) {
                for(Iterator var3 = this.stats.iterator(); var3.hasNext(); ++index) {
                    GameOverStat s = (GameOverStat)var3.next();
                    if (s.hb.hovered) {
                        anyHovered = true;
                        break;
                    }
                }
            }
            
            if (!anyHovered) {
                index = -1;
            }
            
            int numItemsInLeftColumn;
            if (!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed()) {
                if (!CInputActionSet.down.isJustPressed() && !CInputActionSet.altDown.isJustPressed()) {
                    if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                        if (this.stats.size() > 10) {
                            numItemsInLeftColumn = (this.stats.size() - 2) / 2;
                            if (this.stats.size() % 2 != 0) {
                                ++numItemsInLeftColumn;
                            }
                            
                            if (index < numItemsInLeftColumn - 1) {
                                index += numItemsInLeftColumn;
                            } else if (index == numItemsInLeftColumn - 1) {
                                if (this.stats.size() % 2 != 0) {
                                    index += numItemsInLeftColumn - 1;
                                } else {
                                    index += numItemsInLeftColumn;
                                }
                            } else if (index >= numItemsInLeftColumn && index < this.stats.size() - 2) {
                                index -= numItemsInLeftColumn;
                            }
                        }
                        
                        if (index > this.stats.size() - 1) {
                            index = this.stats.size() - 1;
                        }
                        
                        if (index != -1) {
                            CInputHelper.setCursor(((GameOverStat)this.stats.get(index)).hb);
                        }
                    }
                } else {
                    if (index == -1) {
                        index = 0;
                        CInputHelper.setCursor(((GameOverStat)this.stats.get(index)).hb);
                        return;
                    }
                    
                    ++index;
                    if (this.stats.size() > 10) {
                        numItemsInLeftColumn = (this.stats.size() - 2) / 2;
                        if (this.stats.size() % 2 != 0) {
                            ++numItemsInLeftColumn;
                        }
                        
                        if (index == numItemsInLeftColumn) {
                            index = this.stats.size() - 1;
                        }
                    } else {
                        if (index > this.stats.size() - 1) {
                            index = 0;
                        }
                        
                        if (index == this.stats.size() - 2) {
                            ++index;
                        }
                    }
                    
                    if (index > this.stats.size() - 3) {
                        index = this.stats.size() - 1;
                    }
                    
                    CInputHelper.setCursor(((GameOverStat)this.stats.get(index)).hb);
                }
            } else {
                --index;
                if (this.stats.size() > 10) {
                    numItemsInLeftColumn = (this.stats.size() - 2) / 2;
                    if (this.stats.size() % 2 == 0) {
                        --numItemsInLeftColumn;
                    }
                    
                    if (index == numItemsInLeftColumn) {
                        index = this.stats.size() - 1;
                    } else if (index < 0) {
                        index = this.stats.size() - 1;
                    } else if (index == this.stats.size() - 2) {
                        --index;
                    }
                } else if (index < 0) {
                    index = this.stats.size() - 1;
                } else if (index == this.stats.size() - 2) {
                    --index;
                }
                
                CInputHelper.setCursor(((GameOverStat)this.stats.get(index)).hb);
            }
            
        }
    }
    
    private void updateAscensionProgress() {
        if (isVictory && AbstractDungeon.isAscensionMode && Settings.isStandardRun() && (AbstractDungeon.ascensionLevel < 20) && StatsScreen.isPlayingHighestAscension(AbstractDungeon.player.getPrefs())) {
            //bc: no more unlocking new ascensions when you die. worst feeling ever.
            // not only did you just lose, but they took away your ability to earn that particular accomplishment in a future run by giving it to you now.
            StatsScreen.incrementAscension(AbstractDungeon.player.getCharStat());
            AbstractDungeon.topLevelEffects.add(new AscensionLevelUpTextEffect());
        } else if (!AbstractDungeon.ascensionCheck && UnlockTracker.isAscensionUnlocked(AbstractDungeon.player) && !Settings.seedSet) {
            AbstractDungeon.topLevelEffects.add(new AscensionUnlockedTextEffect());
        }
        
    }
    
    private void updateStatsScreen() {
        if (this.showingStats) {
            this.progressBarAlpha = MathHelper.slowColorLerpSnap(this.progressBarAlpha, 1.0F);
            this.statsTimer -= Gdx.graphics.getDeltaTime();
            if (this.statsTimer < 0.0F) {
                this.statsTimer = 0.0F;
            }
            
            this.returnButton.y = Interpolation.pow3In.apply((float)Settings.HEIGHT * 0.1F, (float)Settings.HEIGHT * 0.15F, this.statsTimer * 1.0F / 0.5F);
            AbstractDungeon.dynamicBanner.y = Interpolation.pow3In.apply((float)Settings.HEIGHT / 2.0F + 320.0F * Settings.scale, DynamicBanner.Y, this.statsTimer * 1.0F / 0.5F);
            Iterator var1 = this.stats.iterator();
            
            while(var1.hasNext()) {
                GameOverStat i = (GameOverStat)var1.next();
                i.update();
            }
            
            if (this.statAnimateTimer < 0.0F) {
                boolean allStatsShown = true;
                Iterator var5 = this.stats.iterator();
                
                while(var5.hasNext()) {
                    GameOverStat i = (GameOverStat)var5.next();
                    if (i.hidden) {
                        i.hidden = false;
                        this.statAnimateTimer = 0.1F;
                        allStatsShown = false;
                        break;
                    }
                }
                
                if (allStatsShown) {
                    this.animateProgressBar();
                }
            } else {
                this.statAnimateTimer -= Gdx.graphics.getDeltaTime();
            }
        }
        
    }
    
    public void render(SpriteBatch sb) {
        Iterator i = this.particles.iterator();
        
        DeathScreenFloatyEffect e;
        while(i.hasNext()) {
            e = (DeathScreenFloatyEffect)i.next();
            if (e.renderBehind) {
                e.render(sb);
            }
            
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }
        
        AbstractDungeon.player.render(sb);
        if (this.monsters != null) {
            this.monsters.render(sb);
        }
        
        sb.setBlendFunction(770, 1);
        i = this.particles.iterator();
        
        while(i.hasNext()) {
            e = (DeathScreenFloatyEffect)i.next();
            if (!e.renderBehind) {
                e.render(sb);
            }
        }
        
        sb.setBlendFunction(770, 771);
        this.renderStatsScreen(sb);
        if (!this.showingStats && !isVictory) {
            FontHelper.renderFontCentered(sb, FontHelper.topPanelInfoFont, this.deathText, (float)Settings.WIDTH / 2.0F, DEATH_TEXT_Y, this.deathTextColor);
        }
        
        this.returnButton.render(sb);
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DeathScreen");
        TEXT = uiStrings.TEXT;
        DEATH_TEXT_Y = (float)Settings.HEIGHT - 360.0F * Settings.scale;
    }
}
