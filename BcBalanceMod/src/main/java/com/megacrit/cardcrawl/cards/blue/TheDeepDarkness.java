//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import basemod.abstracts.CustomCard;
import bcBalanceMod.BcBalanceMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DeepDarknessPower;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public class TheDeepDarkness extends CustomCard
{
    public static final String ID = BcBalanceMod.makeID("TheDeepDarkness");
    private static final CardStrings cardStrings;
    
    public TheDeepDarkness()
    {
        super(ID, cardStrings.NAME, makeCardPath("blue/theDeepDarkness.png"), 2, cardStrings.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
    }
    
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToBot(new ApplyPowerAction(p, p, new DeepDarknessPower(p, upgraded), 1));
    }
    
    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeName();
            //this.upgradeBaseCost(2);
            
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    static
    {
        cardStrings = new CardStrings();
        //extra spaces to move it out from under the energy cost
        if (Settings.BIG_TEXT_MODE)
        {
            cardStrings.NAME = "   The Deep Darkness";
        }
        else
        {
            cardStrings.NAME = " The Deep Darkness";
        }
        cardStrings.DESCRIPTION = "End of turn: NL If you have an empty orb slot, NL Channel 1 Dark.";
        cardStrings.UPGRADE_DESCRIPTION = "End of turn: NL If you have an empty orb slot, NL  Channel 1 Dark, NL and trigger its passive.";
    }
}
