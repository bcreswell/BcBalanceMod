//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ReachHeavenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;

public class ReachHeaven extends BcAttackCardBase
{
    public static final String ID = "ReachHeaven";
    
    //region Description
    @Override
    public String getDisplayName()
    {
        return "Reach Heaven";
    }
    
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new ThroughViolence();
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/attack/reach_heaven";
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 10 : 15;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Shuffle a NL *Through *Violence into your draw pile. NL Gain !M! Mantra.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.SLASH_HEAVY));
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
        //gain mantra last so we draw Through Violence if it puts us in Divinity.
        addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
        
       // addToBot(new ReachHeavenAction(cardsToPreview));
    }
}
