package com.megacrit.cardcrawl.potions;

import basemod.abstracts.*;
import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class RetainPotion extends CustomPotion
{
    public static final String POTION_ID = BcBalanceMod.makeID("RetainPotion");
    public static final Color LiquidColor = CardHelper.getColor(209, 53, 255);
    public static final Color HybridColor = CardHelper.getColor(209, 53, 255);
    public static final Color SpotsColor = CardHelper.getColor(150, 150, 200);
    
    public RetainPotion()
    {
        super("Retain Potion", POTION_ID, PotionRarity.UNCOMMON, AbstractPotion.PotionSize.M, PotionColor.BLUE);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        if (potency == 1)
        {
            description = "Retain your Energy, Block, and the cards in your hand, for " + potency + " turn.";
        }
        else
        {
            description = "Retain your Energy, Block, and the cards in your hand, for " + potency + " turns.";
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractPlayer player = AbstractDungeon.player;
            
            addToBot(new BcApplyPowerAction(new ConserveEnergyPower(player, potency)));
            addToBot(new BcApplyPowerAction(new BlurPower(player, potency)));
            addToBot(new BcApplyPowerAction(new EquilibriumPower(player, potency)));
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 1;
    }
    
    public AbstractPotion makeCopy()
    {
        return new RetainPotion();
    }
}
