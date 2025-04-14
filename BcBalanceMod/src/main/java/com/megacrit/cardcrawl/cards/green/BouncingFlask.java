package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;

public class BouncingFlask extends BcSkillCardBase
{
    public static final String ID = "Bouncing Flask";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/bouncing_flask";
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
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        // how many times
        return !upgraded ? 4 : 5;
    }
    
    public int getPoisonAmount()
    {
        return 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Inflict "+getPoisonAmount()+" Poison to a random enemy !M! times.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (randomMonster != null)
        {
            addToBot(new VFXAction(new PotionBounceEffect(player.hb.cX, player.hb.cY, randomMonster.hb.cX, hb.cY), 0.3F));
        }

        addToBot(new BouncingFlaskAction(randomMonster, getPoisonAmount(), magicNumber));
    }
}