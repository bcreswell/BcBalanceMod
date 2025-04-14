package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Alchemize extends BcSkillCardBase
{
    public static final String ID = "Venomology";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/alchemize";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(CardTags.HEALING);
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardColor getCardColor()
    {
        return CardColor.COLORLESS;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Create a random potion.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
    }
}
