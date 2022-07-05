package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;

import java.util.Iterator;

public class ThunderStrike extends BcAttackCardBase
{
    public static final String ID = "Thunder Strike";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Thunder Strike";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/attack/thunder_strike";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.STRIKE);
    }
    
    @Override
    public int getCost()
    {
        return 3;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 7 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage to a random enemy for each Lightning Channeled this combat.";
    }
    //endregion
    
    int getLightningCount()
    {
        int lightningCount = 0;
        for (AbstractOrb orb : AbstractDungeon.actionManager.orbsChanneledThisCombat)
        {
            if (orb instanceof Lightning)
            {
                lightningCount++;
            }
        }
        
        return lightningCount;
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        return "lightning count: "+ getLightningCount();
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int lightningCount = getLightningCount();
        
        for (int i = 0; i < lightningCount; ++i)
        {
            addToBot(new NewThunderStrikeAction(this));
        }
    }
}
