package com.megacrit.cardcrawl.powers;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class CatalysingEnzymePower extends AbstractPower
{
    public static final String POWER_ID = "CatalysingEnzymePower";
    
    private static final Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/catalysingEnzyme36x36.png");
    private static final Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/catalysingEnzyme84x84.png");
    
    private AbstractCreature source;
    
    public CatalysingEnzymePower(AbstractCreature owner, AbstractCreature source, int poisonAmt)
    {
        this.name = "Catalysing Enzyme";
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = poisonAmt;
        if (this.amount >= 9999)
        {
            this.amount = 9999;
        }
        
        this.updateDescription();
        this.region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }
    
    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }
    
    public void updateDescription()
    {
        this.description = "When this takes Poison damage, NL Catalysing Enzyme is consumed to deal extra damage.";
    }
}
