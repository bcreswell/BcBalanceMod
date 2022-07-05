//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class Boot extends AbstractRelic
{
    public static final String ID = "Boot";
    private static final int THRESHOLD = 6;
    
    public Boot()
    {
        super("Boot", "boot.png", RelicTier.COMMON, LandingSound.HEAVY);
    }
    
    public String getUpdatedDescription()
    {
        return "Whenever you would deal at least 1 unblocked attack damage, it will be increased to at least #b" + THRESHOLD + " damage.";
    }
    
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount)
    {
        if ((info.owner != null) &&
                    (info.type != DamageType.HP_LOSS) &&
                    (info.type != DamageType.THORNS) &&
                    (damageAmount > 0) &&
                    (damageAmount < THRESHOLD))
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return THRESHOLD;
        }
        else
        {
            return damageAmount;
        }
    }
    
    public AbstractRelic makeCopy()
    {
        return new Boot();
    }
}
