package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class Inflame extends BcPowerCardBase
{
    public static final String ID = "Inflame";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Burn();
    }
    
    @Override
    public String getImagePath()
    {
        return "red/power/inflame";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Gain !M! Strength. NL Shuffle a *Burn into your draw pile.";
        }
        else
        {
            return "Gain !M! Strength. NL Shuffle a *Burn+ into your draw pile.";
        }
    }
    //endregion
    
    @Override
    protected void onUpgraded()
    {
        cardsToPreview.upgrade();
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
        addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, magicNumber), magicNumber));
        
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview, 1, true, true));
    }
}
