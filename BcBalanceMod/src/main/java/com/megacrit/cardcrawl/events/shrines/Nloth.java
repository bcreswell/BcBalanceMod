package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Nloth extends AbstractImageEvent {
    public static final String ID = "N'loth";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private int screenNum = 0;
    private AbstractRelic choice1;
    private AbstractRelic choice2;
    private AbstractRelic gift;
    
    public Nloth() {
        super(NAME, DIALOG_1, "images/events/nloth.jpg");
        ArrayList<AbstractRelic> relics = new ArrayList();
        for(AbstractRelic r : AbstractDungeon.player.relics)
        {
            if (!r.relicId.equals(BcBalancingScales.ID))
            {
                relics.add(r);
            }
        }
        
        Collections.shuffle(relics, new Random(AbstractDungeon.miscRng.randomLong()));
        this.choice1 = (AbstractRelic)relics.get(0);
        this.choice2 = (AbstractRelic)relics.get(1);
        this.gift = new NlothsGift();
        this.imageEventText.setDialogOption(OPTIONS[0] + this.choice1.name + OPTIONS[1], (AbstractRelic)(new NlothsGift()));
        this.imageEventText.setDialogOption(OPTIONS[0] + this.choice2.name + OPTIONS[1], (AbstractRelic)(new NlothsGift()));
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }
    
    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SERPENT");
        }
        
    }
    
    protected void buttonEffect(int buttonPressed) {
        switch(this.screenNum) {
            case 0:
                switch(buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DIALOG_2);
                        if (AbstractDungeon.player.hasRelic("Nloth's Gift")) {
                            this.gift = new Circlet();
                            AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", this.gift, this.choice1);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.gift);
                        } else {
                            AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", this.gift, this.choice1);
                            AbstractDungeon.player.loseRelic(this.choice1.relicId);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.gift);
                        }
                        
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_2);
                        if (AbstractDungeon.player.hasRelic("Nloth's Gift")) {
                            this.gift = new Circlet();
                            AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", this.gift, this.choice2);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.gift);
                        } else {
                            AbstractEvent.logMetricRelicSwap("N'loth", "Traded Relic", this.gift, this.choice2);
                            AbstractDungeon.player.loseRelic(this.choice2.relicId);
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), this.gift);
                        }
                        
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 2:
                        AbstractEvent.logMetricIgnored("N'loth");
                        this.imageEventText.updateBodyText(DIALOG_3);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        this.imageEventText.updateBodyText(DIALOG_3);
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            case 1:
                this.openMap();
                break;
            default:
                this.openMap();
        }
        
    }
    
    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("N'loth");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }
}
