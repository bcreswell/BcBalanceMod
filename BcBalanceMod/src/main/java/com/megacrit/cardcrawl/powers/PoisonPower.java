package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class PoisonPower extends AbstractPower
{
    public static final String POWER_ID = "Poison";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    
    public PoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt)
    {
        this.name = NAME;
        this.ID = "Poison";
        this.owner = owner;
        this.source = source;
        this.amount = poisonAmt;
        if (this.amount >= 9999)
        {
            this.amount = 9999;
        }
        
        this.updateDescription();
        this.loadRegion("poison");
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }
    
    public void playApplyPowerSfx()
    {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }
    
    public void updateDescription()
    {
        if (this.owner != null && !this.owner.isPlayer)
        {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        }
        else
        {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }
    
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        if (this.amount > 98 && AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT)
        {
            UnlockTracker.unlockAchievement("CATALYST");
        }
    }
    
    public void atStartOfTurn()
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            int extraCataDmg = 0;
            AbstractPower catalysingEnzyme = owner.getPower(CatalysingEnzymePower.POWER_ID);
            if (catalysingEnzyme != null)
            {
                extraCataDmg = Math.min(amount, catalysingEnzyme.amount);
            }
            
            this.flashWithoutSound();
            this.addToBot(new PoisonLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON));
            
            if (extraCataDmg > 0)
            {
                addToBot(new CataLoseHpAction(owner, source, extraCataDmg, AbstractGameAction.AttackEffect.POISON));
            }
        }
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Poison");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
