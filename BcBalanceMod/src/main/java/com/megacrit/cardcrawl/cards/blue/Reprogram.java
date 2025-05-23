//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Reprogram extends BcPowerCardBase
{
    public static final String ID = "Reprogram";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Reprogram";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/reprogramPower.png";
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
        return "Lose !M! Focus. NL Gain !M! Strength. NL Gain !M! Dexterity.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new FocusPower(player, -magicNumber)));
        addToBot(new BcApplyPowerAction(new StrengthPower(player, magicNumber)));
        addToBot(new BcApplyPowerAction(new DexterityPower(player, magicNumber)));
    }
}
