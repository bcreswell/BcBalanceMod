package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class Wish extends BcSkillCardBase
{
    public static final String ID = "Wish";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/wish";
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
        return 3;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }

    public int getRegen()
    {
        return !upgraded ? 6 : 7;
    }
    
    public int getStrength()
    {
        return !upgraded ? 3 : 4;
    }
    
    @Override
    public int getMagicNumber()
    {
        //gold
        return !upgraded ? 25 : 30;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Choose one: NL -Gain "+getRegen()+" Regen, NL -Gain "+getStrength()+" Strength, NL -Gain !M! Gold.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractCard> wishChoices = new ArrayList<>();
        wishChoices.add(new BecomeAlmighty());
        wishChoices.add(new FameAndFortune());
        wishChoices.add(new LiveForever());
        
        if (upgraded)
        {
            for(AbstractCard card : wishChoices)
            {
                card.upgrade();
            }
        }
        
        addToBot(new ChooseOneAction(wishChoices));
    }
    
    public void applyPowers()
    {
    }
    
    public void calculateCardDamage(AbstractMonster mo)
    {
    }
}
