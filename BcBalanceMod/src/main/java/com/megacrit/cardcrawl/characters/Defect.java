//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.BcBalancingScales;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Defect extends AbstractPlayer
{
    private static final Logger logger = LogManager.getLogger(Defect.class.getName());
    private static final CharacterStrings characterStrings;
    public static final String[] NAMES;
    public static final String[] TEXT;
    private EnergyOrbInterface energyOrb = new EnergyOrbBlue();
    private Prefs prefs;
    private CharStat charStat = new CharStat(this);
    
    Defect(String playerName)
    {
        super(playerName, PlayerClass.DEFECT);
        this.drawX += 5.0F * Settings.scale;
        this.drawY += 7.0F * Settings.scale;
        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
        this.initializeClass((String) null, "images/characters/defect/shoulder2.png", "images/characters/defect/shoulder.png", "images/characters/defect/corpse.png", this.getLoadout(), 0.0F, -5.0F, 240.0F, 244.0F, new EnergyManager(3));
        this.loadAnimation("images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.9F);
    }
    
    public String getPortraitImageName()
    {
        return "defectPortrait.jpg";
    }
    
    public ArrayList<String> getStartingRelics()
    {
        ArrayList<String> retVal = new ArrayList();
        retVal.add("Cracked Core");
        UnlockTracker.markRelicAsSeen("Cracked Core");
        retVal.add(BcBalancingScales.ID);
        UnlockTracker.markRelicAsSeen(BcBalancingScales.ID);
        return retVal;
    }
    
    public ArrayList<String> getStartingDeck()
    {
        ArrayList<String> retVal = new ArrayList();
        
        //12 cards total
        
        //3 strikes
        retVal.add("Strike_B");
        retVal.add("Strike_B");
        retVal.add("Strike_B");
        
        //5 defends
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        
        //4 utility/orb manipulation
        retVal.add("Zap");
        retVal.add("Redo"); //recursion
        retVal.add("Dualcast");
        retVal.add("Panacea");
        
        return retVal;
    }
    
    public AbstractCard getStartCardForEvent()
    {
        return new Dualcast(); //new Zap();
    }
    
    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0], 75, 75, 3, 99, 5, this, this.getStartingRelics(), this.getStartingDeck(), false);
    }
    
    public String getTitle(PlayerClass plyrClass)
    {
        return AbstractPlayer.uiStrings.TEXT[3];
    }
    
    public CardColor getCardColor()
    {
        return CardColor.BLUE;
    }
    
    public Color getCardRenderColor()
    {
        return Color.SKY;
    }
    
    public String getAchievementKey()
    {
        return "SAPPHIRE";
    }
    
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool)
    {
        CardLibrary.addBlueCards(tmpPool);
        if (ModHelper.isModEnabled("Red Cards"))
        {
            CardLibrary.addRedCards(tmpPool);
        }
        
        if (ModHelper.isModEnabled("Green Cards"))
        {
            CardLibrary.addGreenCards(tmpPool);
        }
        
        if (ModHelper.isModEnabled("Purple Cards"))
        {
            CardLibrary.addPurpleCards(tmpPool);
        }
        
        return tmpPool;
    }
    
    public Color getCardTrailColor()
    {
        return Color.SKY.cpy();
    }
    
    public String getLeaderboardCharacterName()
    {
        return "DEFECT";
    }
    
    public Texture getEnergyImage()
    {
        return ImageMaster.BLUE_ORB_FLASH_VFX;
    }
    
    public int getAscensionMaxHPLoss()
    {
        return 4;
    }
    
    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontBlue;
    }
    
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        this.energyOrb.renderOrb(sb, enabled, current_x, current_y);
    }
    
    public void updateOrb(int orbCount)
    {
        this.energyOrb.updateOrb(orbCount);
    }
    
    public Prefs getPrefs()
    {
        if (this.prefs == null)
        {
            logger.error("prefs need to be initialized first!");
        }
        
        return this.prefs;
    }
    
    public void loadPrefs()
    {
        this.prefs = SaveHelper.getPrefs("STSDataDefect");
    }
    
    public CharStat getCharStat()
    {
        return this.charStat;
    }
    
    public int getUnlockedCardCount()
    {
        return UnlockTracker.unlockedBlueCardCount;
    }
    
    public int getSeenCardCount()
    {
        return CardLibrary.seenBlueCards;
    }
    
    public int getCardCount()
    {
        return CardLibrary.blueCards;
    }
    
    public boolean saveFileExists()
    {
        return SaveAndContinue.saveExistsAndNotCorrupted(this);
    }
    
    public String getWinStreakKey()
    {
        return "win_streak_defect";
    }
    
    public String getLeaderboardWinStreakKey()
    {
        return "DEFECT_CONSECUTIVE_WINS";
    }
    
    public void renderStatScreen(SpriteBatch sb, float screenX, float renderY)
    {
        if (!UnlockTracker.isCharacterLocked("Defect"))
        {
            if (CardCrawlGame.mainMenuScreen.statsScreen.defectHb == null)
            {
                CardCrawlGame.mainMenuScreen.statsScreen.defectHb = new Hitbox(150.0F * Settings.scale, 150.0F * Settings.scale);
            }
            
            StatsScreen.renderHeader(sb, StatsScreen.NAMES[4], screenX, renderY);
            this.charStat.render(sb, screenX, renderY);
        }
        
    }
    
    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, false);
    }
    
    public String getCustomModeCharacterButtonSoundKey()
    {
        return "ATTACK_MAGIC_BEAM_SHORT";
    }
    
    public Texture getCustomModeCharacterButtonImage()
    {
        return ImageMaster.FILTER_DEFECT;
    }
    
    public CharacterStrings getCharacterString()
    {
        return CardCrawlGame.languagePack.getCharacterString("Defect");
    }
    
    public String getLocalizedCharacterName()
    {
        return NAMES[0];
    }
    
    public void refreshCharStat()
    {
        this.charStat = new CharStat(this);
    }
    
    public AbstractPlayer newInstance()
    {
        return new Defect(this.name);
    }
    
    public AtlasRegion getOrb()
    {
        return AbstractCard.orb_blue;
    }
    
    public void damage(DamageInfo info)
    {
        if (info.owner != null && info.type != DamageType.THORNS && info.output - this.currentBlock > 0)
        {
            TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTime(0.9F);
        }
        
        super.damage(info);
    }
    
    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[10];
    }
    
    public Color getSlashAttackColor()
    {
        return Color.SKY;
    }
    
    public AttackEffect[] getSpireHeartSlashEffect()
    {
        return new AttackEffect[]{AttackEffect.SLASH_HEAVY, AttackEffect.FIRE, AttackEffect.SLASH_DIAGONAL, AttackEffect.SLASH_HEAVY, AttackEffect.FIRE, AttackEffect.SLASH_DIAGONAL};
    }
    
    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[5];
    }
    
    static
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Defect");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
    }
}
