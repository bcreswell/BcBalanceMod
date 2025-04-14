package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;

public class IfYouStrikeMeDown extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("IfYouStrikeMeDown");
    
    //region Description
    @Override
    public String getDisplayName()
    {
        return "If You Strike Me Down";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/ifYouStrikeMeDown.png";
    }
    
    @Override
    public void onInitialized()
    {
        tags.add(AbstractCard.CardTags.STRIKE);
        //cardsToPreview = new Insight();
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
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
    public int getMagicNumber()
    {
        return 0;
    }
    
    @Override
    public String getBaseDescription()
    {
        String description = "";
        if (magicNumber == 1)
        {
            description = "Create an *Insight. NL ";
        }
        else if (magicNumber > 1)
        {
            description = "Create !M! *Insights. NL ";
        }
        description += "If you die, return with " + getHpToReviveWith() + " HP, become Intangible, and next turn enter Divinity.";
        
        //description += "NL If you die: NL Return with " + getHpToReviveWith() + " health, NL Gain 1 Intangible, NL and on the next turn, Enter Divinity.";
        if (BcUtility.playerHasRelic(StrikeDummy.ID))
        {
            description += " NL (Strike Dummy: 2x HP)";
        }
        return description;
    }
    //endregion
    
    public static int getHpToReviveWith()
    {
        int reviveHp = 8;
        if (BcUtility.playerHasRelic(StrikeDummy.ID))
        {
            reviveHp *= 2;
        }
        
        return reviveHp;
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        for (int i = 0; i < magicNumber; i++)
        {
            addToBot(new MakeTempCardInHandAction(new Insight()));
        }
        
        addToTop(new BcApplyPowerAction(new IfYouStrikeMeDownPower(player, 1)));
    }
}
