package com.megacrit.cardcrawl.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.Iterator;

public class UseCardAction extends AbstractGameAction {
    private AbstractCard targetCard;
    public AbstractCreature target;
    public boolean exhaustCard;
    public boolean returnToHand;
    public boolean reboundCard;
    private static final float DUR = 0.15F;
    
    public UseCardAction(AbstractCard card, AbstractCreature target) {
        this.target = null;
        this.reboundCard = false;
        this.targetCard = card;
        this.target = target;
        if (card.exhaustOnUseOnce || card.exhaust) {
            this.exhaustCard = true;
        }
        
        this.setValues(AbstractDungeon.player, (AbstractCreature)null, 1);
        this.duration = 0.15F;
        Iterator var3 = AbstractDungeon.player.powers.iterator();
        
        while(var3.hasNext()) {
            AbstractPower p = (AbstractPower)var3.next();
            if (!card.dontTriggerOnUseCard) {
                p.onUseCard(card, this);
            }
        }
        
        var3 = AbstractDungeon.player.relics.iterator();
        
        while(var3.hasNext()) {
            AbstractRelic r = (AbstractRelic)var3.next();
            if (!card.dontTriggerOnUseCard) {
                r.onUseCard(card, this);
            }
        }
        
        var3 = AbstractDungeon.player.hand.group.iterator();
        
        AbstractCard c;
        while(var3.hasNext()) {
            c = (AbstractCard)var3.next();
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        
        var3 = AbstractDungeon.player.discardPile.group.iterator();
        
        while(var3.hasNext()) {
            c = (AbstractCard)var3.next();
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        
        var3 = AbstractDungeon.player.drawPile.group.iterator();
        
        while(var3.hasNext()) {
            c = (AbstractCard)var3.next();
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        
        var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while(var3.hasNext()) {
            AbstractMonster m = (AbstractMonster)var3.next();
            Iterator var5 = m.powers.iterator();
            
            while(var5.hasNext()) {
                AbstractPower p = (AbstractPower)var5.next();
                if (!card.dontTriggerOnUseCard) {
                    p.onUseCard(card, this);
                }
            }
        }
        
        if (this.exhaustCard) {
            this.actionType = AbstractGameAction.ActionType.EXHAUST;
        } else {
            this.actionType = AbstractGameAction.ActionType.USE;
        }
        
    }
    
    public UseCardAction(AbstractCard targetCard) {
        this(targetCard, (AbstractCreature)null);
    }
    
    public void update() {
        if (this.duration == 0.15F) {
            Iterator var1 = AbstractDungeon.player.powers.iterator();
            
            while(var1.hasNext()) {
                AbstractPower p = (AbstractPower)var1.next();
                if (!this.targetCard.dontTriggerOnUseCard) {
                    p.onAfterUseCard(this.targetCard, this);
                }
            }
            
            var1 = AbstractDungeon.getMonsters().monsters.iterator();
            
            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                Iterator var3 = m.powers.iterator();
                
                while(var3.hasNext()) {
                    AbstractPower p = (AbstractPower)var3.next();
                    if (!this.targetCard.dontTriggerOnUseCard) {
                        p.onAfterUseCard(this.targetCard, this);
                    }
                }
            }
            
            this.targetCard.freeToPlayOnce = false;
            this.targetCard.isInAutoplay = false;
            if (this.targetCard.purgeOnUse) {
                this.addToTop(new ShowCardAndPoofAction(this.targetCard));
                this.isDone = true;
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            
            if (this.targetCard.type == AbstractCard.CardType.POWER) {
                this.addToTop(new ShowCardAction(this.targetCard));
                if (Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1F));
                } else {
                    this.addToTop(new WaitAction(0.7F));
                }
                
                AbstractDungeon.player.hand.empower(this.targetCard);
                this.isDone = true;
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            
            AbstractDungeon.player.cardInUse = null;
            boolean spoonProc = false;
            if (this.exhaustCard && AbstractDungeon.player.hasRelic("Strange Spoon") && this.targetCard.type != AbstractCard.CardType.POWER) {
                spoonProc = AbstractDungeon.cardRandomRng.randomBoolean();
            }
            
            if (this.exhaustCard && !spoonProc) {
                AbstractDungeon.player.hand.moveToExhaustPile(this.targetCard);
                CardCrawlGame.dungeon.checkForPactAchievement();
            } else {
                if (spoonProc) {
                    AbstractDungeon.player.getRelic("Strange Spoon").flash();
                }
                
                targetCard.retain = false;
                if (this.reboundCard) {
                    AbstractDungeon.player.hand.moveToDeck(this.targetCard, false);
                } else if (this.targetCard.shuffleBackIntoDrawPile) {
                    AbstractDungeon.player.hand.moveToDeck(this.targetCard, true);
                } else if (this.targetCard.returnToHand) {
                    AbstractDungeon.player.hand.moveToHand(this.targetCard);
                    AbstractDungeon.player.onCardDrawOrDiscard();
                } else {
                    AbstractDungeon.player.hand.moveToDiscardPile(this.targetCard);
                }
            }
            
            this.targetCard.exhaustOnUseOnce = false;
            this.targetCard.dontTriggerOnUseCard = false;
            this.addToBot(new HandCheckAction());
        }
        
        this.tickDuration();
    }
}
