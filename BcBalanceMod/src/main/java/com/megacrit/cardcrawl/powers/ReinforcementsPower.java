//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ReinforcementsPower extends AbstractPower
{
    public static final String POWER_ID = "Reinforcements";
    
    public ReinforcementsPower(AbstractCreature owner, int amount)
    {
        this.name = POWER_ID;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("demonForm");
    }
    
    public void updateDescription()
    {
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            this.description = "Reinforcements remaining: #b" + amount;
        }
        else
        {
            this.description = amount + " ???";
        }
    }
}
