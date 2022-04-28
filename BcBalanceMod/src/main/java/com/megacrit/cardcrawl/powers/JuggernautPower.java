package com.megacrit.cardcrawl.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class JuggernautPower extends AbstractPower
{
    public static final String POWER_ID = "Juggernaut";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public JuggernautPower(AbstractCreature owner, int newAmount)
    {
        name = NAME;
        ID = "Juggernaut";
        this.owner = owner;
        amount = newAmount;
        updateDescription();
        loadRegion("juggernaut");
    }
    
    public void onGainedBlock(float blockAmount)
    {
        if (blockAmount > 0.0F)
        {
            flash();
            
            int dmgAmount = (int) Math.min(amount, blockAmount);
            addToBot(new DamageRandomEnemyAction(new DamageInfo(owner, dmgAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
    
    public void updateDescription()
    {
        description = "When you gain block, deal up to " + amount + " of it as damage to a random enemy.";
    }
    
    static
    {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Juggernaut");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
