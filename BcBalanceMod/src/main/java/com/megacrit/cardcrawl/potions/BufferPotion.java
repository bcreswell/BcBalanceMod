package com.megacrit.cardcrawl.potions;

import basemod.abstracts.*;
import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.powers.*;

public class BufferPotion extends CustomPotion
{
    public static final String POTION_ID = BcBalanceMod.makeID("BufferPotion");
    public static final Color LiquidColor = CardHelper.getColor(100, 180, 240);
    public static final Color HybridColor = CardHelper.getColor(70, 130, 200);
    
    public BufferPotion()
    {
        super("Buffer Potion", POTION_ID, PotionRarity.UNCOMMON, AbstractPotion.PotionSize.BOTTLE, PotionColor.WHITE);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        description = "Gain #b" + potency + " #yBuffer.";
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.player != null)
        {
            addToBot(new BcApplyPowerAction(new BufferPower(AbstractDungeon.player, potency)));
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 1;
    }
    
    public AbstractPotion makeCopy()
    {
        return new BufferPotion();
    }
}
