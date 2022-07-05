package com.megacrit.cardcrawl.potions;

import basemod.abstracts.*;
import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class MultiBlockPotion extends CustomPotion
{
    public static final String POTION_ID = BcBalanceMod.makeID("MultiBlockPotion");
    public static final Color LiquidColor = CardHelper.getColor(210, 210, 230);
    public static final Color HybridColor = CardHelper.getColor(190, 190, 210);
    
    public MultiBlockPotion()
    {
        super("Multi-Block Potion", POTION_ID, PotionRarity.COMMON, AbstractPotion.PotionSize.SPIKY, PotionColor.WHITE);
        isThrown = false;
    }
    
    public void initializeData()
    {
        potency = getPotency();
        description = "Gain #b" + potency + " #yBlock for each enemy intending to attack you.";
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    
    int getAttackingMonstersCount()
    {
        int count = 0;
        if (BcUtility.isPlayerInCombat())
        {
            for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
            {
                if (!monster.isDeadOrEscaped() && (monster.getIntentBaseDmg() >= 0))
                {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    public void use(AbstractCreature target)
    {
        if (AbstractDungeon.player != null)
        {
            int attackingMonstersCount = getAttackingMonstersCount();
            for (int i = 0; i < attackingMonstersCount; i++)
            {
                addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, potency));
            }
        }
    }
    
    public int getPotency(int ascensionLevel)
    {
        return 8;
    }
    
    public AbstractPotion makeCopy()
    {
        return new MultiBlockPotion();
    }
}
