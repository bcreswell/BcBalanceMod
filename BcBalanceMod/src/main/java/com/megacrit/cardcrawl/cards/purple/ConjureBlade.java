package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.watcher.ConjureBladeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ConjureBlade extends BcSkillCardBase
{
    public static final String ID = "ConjureBlade";
    
    //region card parameters
    @Override
    public void onInitialized()
    {
        cardsToPreview = new Expunger();
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/conjure_blade";
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
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Shuffle an *Expunger into your draw pile that attacks X times.";
        }
        else
        {
            return "Shuffle an *Expunger into your draw pile that attacks X+1 times.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!upgraded)
        {
            addToBot(new ConjureBladeAction(player, freeToPlayOnce, energyOnUse));
        }
        else
        {
            addToBot(new ConjureBladeAction(player, freeToPlayOnce, energyOnUse + 1));
        }
    }
}
