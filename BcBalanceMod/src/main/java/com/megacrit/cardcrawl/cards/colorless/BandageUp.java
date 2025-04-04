package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BandageUp extends BcSkillCardBase
{
    public static final String ID = "Bandage Up";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Bandage Up";
    }
    
    @Override
    public String getImagePath()
    {
        return "colorless/skill/bandage_up";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        //was 4(6)
        return !upgraded ? 6 : 9;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Heal !M! HP.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new HealAction(player, player, magicNumber));
    }
}