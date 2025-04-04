package com.megacrit.cardcrawl.actions;

import bcBalanceMod.BcUtility;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.TriggerEndOfTurnOrbsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Careless;
import com.megacrit.cardcrawl.daily.mods.ControlledChaos;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterQueueItem;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.watcher.NaturalViolentFirePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Calipers;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameActionManager {
    private static final Logger logger = LogManager.getLogger(GameActionManager.class.getName());
    private ArrayList<AbstractGameAction> nextCombatActions = new ArrayList();
    public ArrayList<AbstractGameAction> actions = new ArrayList();
    public ArrayList<AbstractGameAction> preTurnActions = new ArrayList();
    public ArrayList<CardQueueItem> cardQueue = new ArrayList();
    public ArrayList<MonsterQueueItem> monsterQueue = new ArrayList();
    public ArrayList<AbstractCard> cardsPlayedThisTurn = new ArrayList();
    public ArrayList<AbstractCard> cardsPlayedThisCombat = new ArrayList();
    public ArrayList<AbstractOrb> orbsChanneledThisCombat = new ArrayList();
    public ArrayList<AbstractOrb> orbsChanneledThisTurn = new ArrayList();
    public HashMap<String, Integer> uniqueStancesThisCombat = new HashMap();
    public int mantraGained = 0;
    public AbstractGameAction currentAction;
    public AbstractGameAction previousAction;
    public AbstractGameAction turnStartCurrentAction;
    public AbstractCard lastCard = null;
    public GameActionManager.Phase phase;
    public boolean hasControl;
    public boolean turnHasEnded;
    public boolean usingCard;
    public boolean monsterAttacksQueued;
    public static int totalDiscardedThisTurn = 0;
    public static int damageReceivedThisTurn = 0;
    public static int damageReceivedThisCombat = 0;
    public static int hpLossThisCombat = 0;
    public static int playerHpLastTurn;
    public static int energyGainedThisCombat;
    public static int turn = 0;
    
    public GameActionManager() {
        this.phase = GameActionManager.Phase.WAITING_ON_USER;
        this.hasControl = true;
        this.turnHasEnded = false;
        this.usingCard = false;
        this.monsterAttacksQueued = true;
    }
    
    public void addToNextCombat(AbstractGameAction action) {
        this.nextCombatActions.add(action);
    }
    
    public void useNextCombatActions() {
        Iterator var1 = this.nextCombatActions.iterator();
        
        while(var1.hasNext()) {
            AbstractGameAction a = (AbstractGameAction)var1.next();
            this.addToBottom(a);
        }
        
        this.nextCombatActions.clear();
    }
    
    public void addToBottom(AbstractGameAction action) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.actions.add(action);
        }
        
    }
    
    public void addCardQueueItem(CardQueueItem c, boolean inFrontOfQueue) {
        if (inFrontOfQueue) {
            if (!AbstractDungeon.actionManager.cardQueue.isEmpty()) {
                AbstractDungeon.actionManager.cardQueue.add(1, c);
            } else {
                AbstractDungeon.actionManager.cardQueue.add(c);
            }
        } else {
            AbstractDungeon.actionManager.cardQueue.add(c);
        }
        
    }
    
    public void addCardQueueItem(CardQueueItem c) {
        this.addCardQueueItem(c, false);
    }
    
    public void removeFromQueue(AbstractCard c) {
        int index = -1;
        
        for(int i = 0; i < this.cardQueue.size(); ++i) {
            if (((CardQueueItem)this.cardQueue.get(i)).card != null && ((CardQueueItem)this.cardQueue.get(i)).card.equals(c)) {
                index = i;
                break;
            }
        }
        
        if (index != -1) {
            this.cardQueue.remove(index);
        }
        
    }
    
    public void clearPostCombatActions() {
        Iterator i = this.actions.iterator();
        
        while(i.hasNext()) {
            AbstractGameAction e = (AbstractGameAction)i.next();
            if (!(e instanceof HealAction) && !(e instanceof GainBlockAction) && !(e instanceof UseCardAction) && e.actionType != AbstractGameAction.ActionType.DAMAGE) {
                i.remove();
            }
        }
        
    }
    
    public void addToTop(AbstractGameAction action) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.actions.add(0, action);
        }
        
    }
    
    public void addToTurnStart(AbstractGameAction action) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.preTurnActions.add(0, action);
        }
        
    }
    
    public void update() {
        switch(this.phase) {
            case WAITING_ON_USER:
                this.getNextAction();
                break;
            case EXECUTING_ACTIONS:
                if (this.currentAction != null && !this.currentAction.isDone) {
                    this.currentAction.update();
                } else {
                    this.previousAction = this.currentAction;
                    this.currentAction = null;
                    this.getNextAction();
                    if (this.currentAction == null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !this.usingCard) {
                        this.phase = GameActionManager.Phase.WAITING_ON_USER;
                        AbstractDungeon.player.hand.refreshHandLayout();
                        this.hasControl = false;
                    }
                    
                    this.usingCard = false;
                }
                break;
            default:
                logger.info("This should never be called");
        }
        
    }
    
    public void endTurn() {
        AbstractDungeon.player.resetControllerValues();
        this.turnHasEnded = true;
        playerHpLastTurn = AbstractDungeon.player.currentHealth;
    }
    
    private void getNextAction() {
        if (!this.actions.isEmpty()) {
            this.currentAction = (AbstractGameAction)this.actions.remove(0);
            this.phase = GameActionManager.Phase.EXECUTING_ACTIONS;
            this.hasControl = true;
        } else if (!this.preTurnActions.isEmpty()) {
            this.currentAction = (AbstractGameAction)this.preTurnActions.remove(0);
            this.phase = GameActionManager.Phase.EXECUTING_ACTIONS;
            this.hasControl = true;
        } else if (!this.cardQueue.isEmpty()) {
            this.usingCard = true;
            AbstractCard c = ((CardQueueItem)this.cardQueue.get(0)).card;
            if (c == null) {
                this.callEndOfTurnActions();
            } else if (c.equals(this.lastCard)) {
                logger.info("Last card! " + c.name);
                this.lastCard = null;
            }
            
            if (this.cardQueue.size() == 1 && ((CardQueueItem)this.cardQueue.get(0)).isEndTurnAutoPlay) {
                AbstractRelic top = AbstractDungeon.player.getRelic("Unceasing Top");
                if (top != null) {
                    ((UnceasingTop)top).disableUntilTurnEnds();
                }
            }
            
            boolean canPlayCard = false;
            if (c != null) {
                c.isInAutoplay = ((CardQueueItem)this.cardQueue.get(0)).autoplayCard;
            }
            
            if (c != null && ((CardQueueItem)this.cardQueue.get(0)).randomTarget) {
                ((CardQueueItem)this.cardQueue.get(0)).monster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
            }
            
            Iterator i;
            AbstractCard card;
            if (((CardQueueItem)this.cardQueue.get(0)).card == null || !c.canUse(AbstractDungeon.player, ((CardQueueItem)this.cardQueue.get(0)).monster) && !((CardQueueItem)this.cardQueue.get(0)).card.dontTriggerOnUseCard) {
                i = AbstractDungeon.player.limbo.group.iterator();
                
                while(i.hasNext()) {
                    card = (AbstractCard)i.next();
                    if (card == c) {
                        c.fadingOut = true;
                        AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                        i.remove();
                    }
                }
                
                if (c != null) {
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, c.cantUseMessage, true));
                }
            } else {
                canPlayCard = true;
                if (c.freeToPlay()) {
                    //commenting this out fixed issue between forethought and swivel
                    //c.freeToPlayOnce = true;
                }
                
                ((CardQueueItem)this.cardQueue.get(0)).card.energyOnUse = ((CardQueueItem)this.cardQueue.get(0)).energyOnUse;
                if (c.isInAutoplay) {
                    ((CardQueueItem)this.cardQueue.get(0)).card.ignoreEnergyOnUse = true;
                } else {
                    ((CardQueueItem)this.cardQueue.get(0)).card.ignoreEnergyOnUse = ((CardQueueItem)this.cardQueue.get(0)).ignoreEnergyTotal;
                }
                
                if (!((CardQueueItem)this.cardQueue.get(0)).card.dontTriggerOnUseCard) {
                    i = AbstractDungeon.player.powers.iterator();
                    
                    while(i.hasNext()) {
                        AbstractPower p = (AbstractPower)i.next();
                        p.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                    }
                    
                    i = AbstractDungeon.getMonsters().monsters.iterator();
                    
                    while(i.hasNext()) {
                        AbstractMonster m = (AbstractMonster)i.next();
                        Iterator var5 = m.powers.iterator();
                        
                        while(var5.hasNext()) {
                            AbstractPower p = (AbstractPower)var5.next();
                            p.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                        }
                    }
                    
                    i = AbstractDungeon.player.relics.iterator();
                    
                    while(i.hasNext()) {
                        AbstractRelic r = (AbstractRelic)i.next();
                        r.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                    }
                    
                    AbstractDungeon.player.stance.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card);
                    i = AbstractDungeon.player.blights.iterator();
                    
                    while(i.hasNext()) {
                        AbstractBlight b = (AbstractBlight)i.next();
                        b.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                    }
                    
                    i = AbstractDungeon.player.hand.group.iterator();
                    
                    while(i.hasNext()) {
                        card = (AbstractCard)i.next();
                        card.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                    }
                    
                    i = AbstractDungeon.player.discardPile.group.iterator();
                    
                    while(i.hasNext()) {
                        card = (AbstractCard)i.next();
                        card.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                    }
                    
                    i = AbstractDungeon.player.drawPile.group.iterator();
                    
                    while(i.hasNext()) {
                        card = (AbstractCard)i.next();
                        card.onPlayCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster);
                    }
                    
                    ++AbstractDungeon.player.cardsPlayedThisTurn;
                    this.cardsPlayedThisTurn.add(((CardQueueItem)this.cardQueue.get(0)).card);
                    this.cardsPlayedThisCombat.add(((CardQueueItem)this.cardQueue.get(0)).card);
                }
                
                if (this.cardsPlayedThisTurn.size() == 25) {
                    UnlockTracker.unlockAchievement("INFINITY");
                }
                
                if (this.cardsPlayedThisTurn.size() >= 20 && !CardCrawlGame.combo) {
                    CardCrawlGame.combo = true;
                }
                
                if (((CardQueueItem)this.cardQueue.get(0)).card instanceof Shiv) {
                    int shivCount = 0;
                    Iterator var15 = this.cardsPlayedThisTurn.iterator();
                    
                    while(var15.hasNext()) {
                        AbstractCard s = (AbstractCard)var15.next();
                        if (s instanceof Shiv) {
                            ++shivCount;
                            if (shivCount == 10) {
                                UnlockTracker.unlockAchievement("NINJA");
                                break;
                            }
                        }
                    }
                }
                
                if (((CardQueueItem)this.cardQueue.get(0)).card != null) {
                    if (((CardQueueItem)this.cardQueue.get(0)).card.target != AbstractCard.CardTarget.ENEMY || ((CardQueueItem)this.cardQueue.get(0)).monster != null && !((CardQueueItem)this.cardQueue.get(0)).monster.isDeadOrEscaped()) {
                        AbstractDungeon.player.useCard(((CardQueueItem)this.cardQueue.get(0)).card, ((CardQueueItem)this.cardQueue.get(0)).monster, ((CardQueueItem)this.cardQueue.get(0)).energyOnUse);
                    } else {
                        i = AbstractDungeon.player.limbo.group.iterator();
                        
                        while(i.hasNext()) {
                            card = (AbstractCard)i.next();
                            if (card == ((CardQueueItem)this.cardQueue.get(0)).card) {
                                ((CardQueueItem)this.cardQueue.get(0)).card.fadingOut = true;
                                AbstractDungeon.effectList.add(new ExhaustCardEffect(((CardQueueItem)this.cardQueue.get(0)).card));
                                i.remove();
                            }
                        }
                        
                        if (((CardQueueItem)this.cardQueue.get(0)).monster == null) {
                            ((CardQueueItem)this.cardQueue.get(0)).card.drawScale = ((CardQueueItem)this.cardQueue.get(0)).card.targetDrawScale;
                            ((CardQueueItem)this.cardQueue.get(0)).card.angle = ((CardQueueItem)this.cardQueue.get(0)).card.targetAngle;
                            ((CardQueueItem)this.cardQueue.get(0)).card.current_x = ((CardQueueItem)this.cardQueue.get(0)).card.target_x;
                            ((CardQueueItem)this.cardQueue.get(0)).card.current_y = ((CardQueueItem)this.cardQueue.get(0)).card.target_y;
                            AbstractDungeon.effectList.add(new ExhaustCardEffect(((CardQueueItem)this.cardQueue.get(0)).card));
                        }
                    }
                }
            }
            
            this.cardQueue.remove(0);
            if (!canPlayCard && c != null && c.isInAutoplay) {
                c.dontTriggerOnUseCard = true;
                AbstractDungeon.actionManager.addToBottom(new UseCardAction(c));
            }
        } else if (!this.monsterAttacksQueued) {
            this.monsterAttacksQueued = true;
            if (!AbstractDungeon.getCurrRoom().skipMonsterTurn) {
                AbstractDungeon.getCurrRoom().monsters.queueMonsters();
            }
        } else if (!this.monsterQueue.isEmpty()) {
            AbstractMonster m = ((MonsterQueueItem)this.monsterQueue.get(0)).monster;
            if (!m.isDeadOrEscaped() || m.halfDead) {
                if (m.intent != AbstractMonster.Intent.NONE) {
                    this.addToBottom(new ShowMoveNameAction(m));
                    this.addToBottom(new IntentFlashAction(m));
                }
                
                if (!(Boolean)TipTracker.tips.get("INTENT_TIP") && AbstractDungeon.player.currentBlock == 0 && (m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == AbstractMonster.Intent.ATTACK_BUFF || m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
                    if (AbstractDungeon.floorNum <= 5) {
                        ++TipTracker.blockCounter;
                    } else {
                        TipTracker.neverShowAgain("INTENT_TIP");
                    }
                }
                
                m.takeTurn();
                m.applyTurnPowers();
            }
            
            this.monsterQueue.remove(0);
            if (this.monsterQueue.isEmpty()) {
                this.addToBottom(new WaitAction(1.5F));
            }
        } else if (this.turnHasEnded && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (!AbstractDungeon.getCurrRoom().skipMonsterTurn) {
                AbstractDungeon.getCurrRoom().monsters.applyEndOfTurnPowers();
            }
            
            AbstractDungeon.player.cardsPlayedThisTurn = 0;
            this.orbsChanneledThisTurn.clear();
            if (ModHelper.isModEnabled("Careless")) {
                Careless.modAction();
            }
            
            if (ModHelper.isModEnabled("ControlledChaos")) {
                ControlledChaos.modAction();
                AbstractDungeon.player.hand.applyPowers();
            }
            
            AbstractDungeon.player.applyStartOfTurnRelics();
            AbstractDungeon.player.applyStartOfTurnPreDrawCards();
            AbstractDungeon.player.applyStartOfTurnCards();
            AbstractDungeon.player.applyStartOfTurnPowers();
            AbstractDungeon.player.applyStartOfTurnOrbs();
            ++turn;
            AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
            this.turnHasEnded = false;
            totalDiscardedThisTurn = 0;
            this.cardsPlayedThisTurn.clear();
            damageReceivedThisTurn = 0;

            if (!AbstractDungeon.player.hasPower("Barricade") &&
                !AbstractDungeon.player.hasPower("Blur") &&
                (!BcUtility.isPlayerInStance(WrathStance.STANCE_ID) || !AbstractDungeon.player.hasPower(NaturalViolentFirePower.POWER_ID)))
            {
                if (!AbstractDungeon.player.hasRelic("Calipers")) {
                    AbstractDungeon.player.loseBlock();
                } else {
                    AbstractDungeon.player.loseBlock(Calipers.BLOCK_LOSS);
                }
            }
            
            if (!AbstractDungeon.getCurrRoom().isBattleOver) {
                this.addToBottom(new DrawCardAction((AbstractCreature)null, AbstractDungeon.player.gameHandSize, true));
                AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
                AbstractDungeon.player.applyStartOfTurnPostDrawPowers();
                this.addToBottom(new EnableEndTurnButtonAction());
            }
        }
        
    }
    
    private void callEndOfTurnActions() {
        AbstractDungeon.getCurrRoom().applyEndOfTurnRelics();
        AbstractDungeon.getCurrRoom().applyEndOfTurnPreCardPowers();
        this.addToBottom(new TriggerEndOfTurnOrbsAction());
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();
        
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.triggerOnEndOfTurnForPlayingCard();
        }
        
        AbstractDungeon.player.stance.onEndOfTurn();
    }
    
    public void callEndTurnEarlySequence() {
        Iterator var1 = AbstractDungeon.actionManager.cardQueue.iterator();
        
        while(var1.hasNext()) {
            CardQueueItem i = (CardQueueItem)var1.next();
            if (i.autoplayCard) {
                i.card.dontTriggerOnUseCard = true;
                AbstractDungeon.actionManager.addToBottom(new UseCardAction(i.card));
            }
        }
        
        AbstractDungeon.actionManager.cardQueue.clear();
        var1 = AbstractDungeon.player.limbo.group.iterator();
        
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        }
        
        AbstractDungeon.player.limbo.group.clear();
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);
    }
    
    public void cleanCardQueue() {
        Iterator i = this.cardQueue.iterator();
        
        while(i.hasNext()) {
            CardQueueItem e = (CardQueueItem)i.next();
            if (AbstractDungeon.player.hand.contains(e.card)) {
                i.remove();
            }
        }
        
        AbstractCard e;
        for(i = AbstractDungeon.player.limbo.group.iterator(); i.hasNext(); e.fadingOut = true) {
            e = (AbstractCard)i.next();
        }
        
    }
    
    public boolean isEmpty() {
        return this.actions.isEmpty();
    }
    
    public void clearNextRoomCombatActions() {
        this.nextCombatActions.clear();
    }
    
    public void clear() {
        this.actions.clear();
        this.preTurnActions.clear();
        this.currentAction = null;
        this.previousAction = null;
        this.turnStartCurrentAction = null;
        this.cardsPlayedThisCombat.clear();
        this.cardsPlayedThisTurn.clear();
        this.orbsChanneledThisCombat.clear();
        this.orbsChanneledThisTurn.clear();
        this.uniqueStancesThisCombat.clear();
        this.cardQueue.clear();
        energyGainedThisCombat = 0;
        this.mantraGained = 0;
        damageReceivedThisCombat = 0;
        damageReceivedThisTurn = 0;
        hpLossThisCombat = 0;
        this.turnHasEnded = false;
        turn = 1;
        this.phase = GameActionManager.Phase.WAITING_ON_USER;
        totalDiscardedThisTurn = 0;
    }
    
    public static void incrementDiscard(boolean endOfTurn) {
        ++totalDiscardedThisTurn;
        if (!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn) {
            AbstractDungeon.player.updateCardsOnDiscard();
            Iterator var1 = AbstractDungeon.player.relics.iterator();
            
            while(var1.hasNext()) {
                AbstractRelic r = (AbstractRelic)var1.next();
                r.onManualDiscard();
            }
        
            for(AbstractPower power : AbstractDungeon.player.powers)
            {
                //onCardDrawOrDiscard() can't distinguish draw from discard using GameActionManager.totalDiscardedThisTurn w/ tools of the trade
                if (power instanceof BcPowerBase)
                {
                    ((BcPowerBase)power).onManualDiscard();
                }
            }
        }
        
    }
    
    public void updateEnergyGain(int energyGain) {
        energyGainedThisCombat += energyGain;
    }
    
    public static void queueExtraCard(AbstractCard card, AbstractMonster m) {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        int extraCount = 0;
        Iterator var4 = AbstractDungeon.actionManager.cardQueue.iterator();
        
        while(var4.hasNext()) {
            CardQueueItem c = (CardQueueItem)var4.next();
            if (c.card.uuid.equals(card.uuid)) {
                ++extraCount;
            }
        }
        
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        switch(extraCount) {
            case 0:
                tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.xScale;
                break;
            case 1:
                tmp.target_x = (float)Settings.WIDTH / 2.0F + 300.0F * Settings.xScale;
                break;
            case 2:
                tmp.target_x = (float)Settings.WIDTH / 2.0F - 600.0F * Settings.xScale;
                break;
            case 3:
                tmp.target_x = (float)Settings.WIDTH / 2.0F + 600.0F * Settings.xScale;
                break;
            default:
                tmp.target_x = MathUtils.random((float)Settings.WIDTH * 0.2F, (float)Settings.WIDTH * 0.8F);
                tmp.target_y = MathUtils.random((float)Settings.HEIGHT * 0.3F, (float)Settings.HEIGHT * 0.7F);
        }
        
        if (m != null) {
            tmp.calculateCardDamage(m);
        }
        
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }
    
    public static enum Phase {
        WAITING_ON_USER,
        EXECUTING_ACTIONS;
        
        private Phase() {
        }
    }
}
