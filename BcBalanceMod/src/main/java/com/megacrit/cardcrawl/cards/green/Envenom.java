//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BcEnvenomPower;

public class Envenom extends BcPowerCardBase
{
    public static final String ID = "Envenom";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/power/envenom";
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
    public int getCost()
    {
        return !upgraded ? 1 : 0;
    }

    @Override
    public String getBaseDescription()
    {
        return "Whenever an Attack deals unblocked damage, inflict 1 Poison.";
        
//        if (!upgraded)
//        {
//            return "Whenever an Attack deals unblocked damage, inflict 1 Poison.";
//        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        BcEnvenomPower envenomPower = new BcEnvenomPower(player, 1);
//        if (upgraded)
//        {
//            envenomPower.upgrade();
//        }
        
        addToBot(new BcApplyPowerAction(envenomPower));
    }
}
