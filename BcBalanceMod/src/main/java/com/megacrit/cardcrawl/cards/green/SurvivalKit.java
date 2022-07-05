
package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ChooseOneCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class SurvivalKit extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("SurvivalKit");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Survival Kit";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
    }
    
    @Override
    public String getImagePath()
    {
        return "green/survivalKit.png";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Create one: NL - *Bandage *Up, NL - *Panacea or NL - *Purity.";
        }
        else
        {
            return "Create one: NL - *Bandage *Up+, NL - *Panacea+ or NL - *Purity+.";
        }
    }
    //endregion
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        ArrayList<AbstractCard> choices = new ArrayList();
        choices.add(new BandageUp());
        choices.add(new Panacea());
        choices.add(new Purity());
        
        if (upgraded)
        {
            for (AbstractCard card : choices)
            {
                card.upgrade();
            }
        }
        
        addToBot(new ChooseOneCard(choices));
    }
}
