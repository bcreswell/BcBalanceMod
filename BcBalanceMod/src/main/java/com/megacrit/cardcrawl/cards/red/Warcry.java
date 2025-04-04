package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.actions.common.*;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.BcRetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.NoBlockNextTurnPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

public class Warcry extends BcSkillCardBase
{
    public static final String ID = "Warcry";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/warcry";
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
    public int getCost()
    {
        return 0;
    }

    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 4;
    }

    public int getTempStrength()
    {
        return !upgraded ? 2 : 3;
    }

    @Override
    public String getBaseDescription()
    {
        return "Draw "+ BcUtility.getCardCountString(getMagicNumber())+". NL Gain "+getTempStrength()+" Temporary Strength. NL NL Next turn, you can't gain Block from cards.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int str = getTempStrength();
        
        addToBot(new VFXAction(player, new ShockWaveEffect(player.hb.cX, player.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveType.ADDITIVE), 0.5F));
        addToBot(new DrawCardAction(player, magicNumber));
        addToBot(new BcApplyPowerAction(new StrengthPower(player, str)));
        addToBot(new BcApplyPowerAction(new LoseStrengthPower(player, str)));
        addToBot(new BcApplyPowerAction(new NoBlockNextTurnPower(player, 1)));

//        int retainableCards = 0;
//        for (AbstractCard card : AbstractDungeon.player.hand.group)
//        {
//            if (!card.selfRetain && !card.retain && !card.isEthereal)
//            {
//                retainableCards++;
//            }
//        }

//        if ((retainableCards > 0) &&
//            !AbstractDungeon.player.hasRelic("Runic Pyramid") &&
//            !AbstractDungeon.player.hasPower("Equilibrium"))
//        {
//            addToBot(new BcRetainCardsAction(player, 1));
//        }
    }
}
