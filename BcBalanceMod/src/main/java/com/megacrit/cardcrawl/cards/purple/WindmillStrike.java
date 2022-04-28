package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WindmillStrike extends BcAttackCardBase
{
    public static final String ID = "WindmillStrike";
    int retainCount = 0;
    
    //region card parameters
    @Override
    public void onInitialized()
    {
        tags.add(AbstractCard.CardTags.STRIKE);
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/attack/windmill_strike";
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 4 : 5;
    }
    
    public int getAttackCount()
    {
        return  retainCount + 2;
        //return !upgraded ? retainCount + 2 : retainCount + 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage " + getAttackCount() + " times. NL When Retained, increase the number of attacks by 1.";
    }
    //endregion
    
    public void onRetained()
    {
        retainCount++;
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < getAttackCount(); i++)
        {
            addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
}
