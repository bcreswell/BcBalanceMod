package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.watcher.CollectAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Collect extends BcSkillCardBase
{
    public static final String ID = "Collect";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Miracle();
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/collect";
    }
    
    @Override
    public int getCost()
    {
        return -1;
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Put a *Miracle into your hand at the start of your next X+1 turns.";
        }
        else
        {
            return "Put a *Miracle+ into your hand at the start of your next X+1 turns. NL (Master Reality: X+2)";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new CollectAction(player, freeToPlayOnce, energyOnUse, upgraded));
    }
}
