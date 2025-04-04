//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MummifiedHand extends AbstractRelic {
    private static final Logger logger = LogManager.getLogger(MummifiedHand.class.getName());
    public static final String ID = "Mummified Hand";

    public MummifiedHand() {
        super("Mummified Hand", "mummifiedHand.png", RelicTier.BOSS, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return "Whenever you play a Power, a random card in your hand costs 1 less this turn.";
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == CardType.POWER) {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            ArrayList<AbstractCard> groupCopy = new ArrayList();
            Iterator var4 = AbstractDungeon.player.hand.group.iterator();

            while(true) {
                while(var4.hasNext()) {
                    AbstractCard c = (AbstractCard)var4.next();
                    if (c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce) {
                        groupCopy.add(c);
                    } else {
                        logger.info("COST IS 0: " + c.name);
                    }
                }

                var4 = AbstractDungeon.actionManager.cardQueue.iterator();

                while(var4.hasNext()) {
                    CardQueueItem i = (CardQueueItem)var4.next();
                    if (i.card != null) {
                        logger.info("INVALID: " + i.card.name);
                        groupCopy.remove(i.card);
                    }
                }

                AbstractCard c = null;
                if (groupCopy.isEmpty()) {
                    logger.info("NO VALID CARDS");
                } else {
                    logger.info("VALID CARDS: ");
                    Iterator var9 = groupCopy.iterator();

                    while(var9.hasNext()) {
                        AbstractCard cc = (AbstractCard)var9.next();
                        logger.info(cc.name);
                    }

                    c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
                }

                if (c != null) {
                    logger.info("Mummified hand: " + c.name);
                    if (c.costForTurn > 0) c.setCostForTurn(c.costForTurn - 1);
                } else {
                    logger.info("ERROR: MUMMIFIED HAND NOT WORKING");
                }
                break;
            }
        }

    }

    public AbstractRelic makeCopy() {
        return new MummifiedHand();
    }
}
