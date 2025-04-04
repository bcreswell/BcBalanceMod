package com.megacrit.cardcrawl.events.city;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.BloodVial;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;

public class Vampires extends AbstractImageEvent {
    public static final String ID = "Vampires";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String ACCEPT_BODY;
    private static final String EXIT_BODY;
    private static final String GIVE_VIAL;
    private static final float HP_DRAIN = 0.2F; //0.3F;
    private int maxHpLoss;
    private int screenNum = 0;
    private boolean hasVial;
    private List<String> bites;

    public Vampires() {
        super(NAME, "test", "images/events/vampires.jpg");
        this.body = AbstractDungeon.player.getVampireText();
        this.maxHpLoss = MathUtils.ceil((float)AbstractDungeon.player.maxHealth * HP_DRAIN);
        if (this.maxHpLoss >= AbstractDungeon.player.maxHealth) {
            this.maxHpLoss = AbstractDungeon.player.maxHealth - 1;
        }

        this.bites = new ArrayList<>();
        this.hasVial = AbstractDungeon.player.hasRelic("Blood Vial");
        this.imageEventText.setDialogOption(OPTIONS[0] + this.maxHpLoss + OPTIONS[1], new Bite());
        if (this.hasVial) {
            String vialName = (new BloodVial()).name;
            this.imageEventText.setDialogOption(OPTIONS[3] + vialName + OPTIONS[4], new Bite());
        }

        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    protected void buttonEffect(int buttonPressed) {
        switch (screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                        imageEventText.updateBodyText(ACCEPT_BODY);
                        AbstractDungeon.player.decreaseMaxHealth(maxHpLoss);
                        replaceAttacks();
                        logMetricObtainCardsLoseMapHP("Vampires", "Became a vampire", bites, maxHpLoss);
                        screenNum = 1;
                        imageEventText.updateDialogOption(0, OPTIONS[5]);
                        imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        if (hasVial) {
                            CardCrawlGame.sound.play("EVENT_VAMP_BITE");
                            imageEventText.updateBodyText(GIVE_VIAL);
                            AbstractDungeon.player.loseRelic("Blood Vial");
                            replaceAttacks();
                            logMetricObtainCardsLoseRelic("Vampires", "Became a vampire (Vial)", bites, new BloodVial());
                            screenNum = 1;
                            imageEventText.updateDialogOption(0, OPTIONS[5]);
                            imageEventText.clearRemainingOptions();
                            return;
                        }
                    default:
                        logMetricIgnored("Vampires");
                        imageEventText.updateBodyText(EXIT_BODY);
                        screenNum = 2;
                        imageEventText.updateDialogOption(0, OPTIONS[5]);
                        imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:
                openMap();
                break;
            default:
                openMap();
        }

    }

    private void replaceAttacks() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;

        int i;
        for(i = masterDeck.size() - 1; i >= 0; --i) {
            AbstractCard card = (AbstractCard)masterDeck.get(i);
            if (card.tags.contains(CardTags.STARTER_STRIKE)) {
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
        }

        for(i = 0; i < 5; ++i) {
            AbstractCard c = new Bite();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            bites.add(c.cardID);
        }

    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("Vampires");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        ACCEPT_BODY = DESCRIPTIONS[2];
        EXIT_BODY = DESCRIPTIONS[3];
        GIVE_VIAL = DESCRIPTIONS[4];
    }
}
