package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.BcPowerBase;
import bcBalanceMod.baseCards.BcPowerCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class PoisonPower extends BcPowerBase
{
    public static final String POWER_ID = "Poison";
    public boolean isQuiet;
    
    private AbstractCreature source;

    //region parameters
    @Override
    public String getDisplayName() {
        return "Poison";
    }

    @Override
    public String getId() {
        return POWER_ID;
    }

    @Override
    public String getImagePath() {
        return "poison";
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }

    @Override
    public boolean getCanGoNegative() {
        return false;
    }

    @Override
    public String getBaseDescription() {
        return "Lose "+ amount + " health at the start of the turn, then reduce this amount by 1.";
    }
    //endregion

    public PoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt)
    {
        super(owner, poisonAmt);
        this.source = source;
        if (amount >= 9999)
        {
            amount = 9999;
        }
        
        isTurnBased = true;
    }
    
    public void playApplyPowerSfx()
    {
        if (!isQuiet)
        {
            CardCrawlGame.sound.play("POWER_POISON", 0.05F);
        }
    }

    @Override
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        
        if (amount > 98 && AbstractDungeon.player.chosenClass == AbstractPlayer.PlayerClass.THE_SILENT)
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
            boolean isIntangible = owner.hasPower(IntangiblePower.POWER_ID);
            if (isIntangible)
            {
                extraCataDmg = Math.min(1, extraCataDmg);
            }
            
            flashWithoutSound();
            addToBot(new PoisonLoseHpAction(owner, source, amount, AbstractGameAction.AttackEffect.POISON));
            
            if (extraCataDmg > 0)
            {
                addToBot(new CataLoseHpAction(owner, source, extraCataDmg, AbstractGameAction.AttackEffect.POISON));
            }
        }
    }
}
