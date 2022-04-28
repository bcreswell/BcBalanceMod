//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.screens.select;

import bcBalanceMod.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BossChestShineEffect;
import de.robojumper.ststwitch.TwitchPanel;
import de.robojumper.ststwitch.TwitchVoteListener;
import de.robojumper.ststwitch.TwitchVoteOption;
import de.robojumper.ststwitch.TwitchVoter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BossRelicSelectScreen
{
    private static final Logger logger = LogManager.getLogger(BossRelicSelectScreen.class.getName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean isDone = false;
    public ArrayList<AbstractRelic> relics = new ArrayList();
    public ArrayList<AbstractBlight> blights = new ArrayList();
    private MenuCancelButton cancelButton = new MenuCancelButton();
    private static final String SELECT_MSG;
    private Texture smokeImg;
    private float shineTimer = 0.0F;
    private static final float SHINE_INTERAL = 0.1F;
    private static final float BANNER_Y;
    private static final float SLOT_1_X;
    private static final float SLOT_1_Y;
    private static final float SLOT_2_X;
    private static final float SLOT_2_Y;
    private static final float SLOT_3_X;
    private static final float SLOT_3_Y;
    private static final float SLOT_4_X;
    private static final float SLOT_4_Y;
    private static final float SLOT_5_X;
    private static final float SLOT_5_Y;
    private final float B_SLOT_1_X;
    private final float B_SLOT_1_Y;
    private final float B_SLOT_2_X;
    public ConfirmButton confirmButton;
    public AbstractRelic touchRelic;
    public AbstractBlight touchBlight;
    boolean isVoting;
    boolean mayVote;
    
    public BossRelicSelectScreen()
    {
        this.B_SLOT_1_X = 844.0F * Settings.scale;
        this.B_SLOT_1_Y = AbstractDungeon.floorY + 310.0F * Settings.scale;
        this.B_SLOT_2_X = 1084.0F * Settings.scale;
        this.confirmButton = new ConfirmButton();
        this.touchRelic = null;
        this.touchBlight = null;
        this.isVoting = false;
        this.mayVote = false;
    }
    
    public void update()
    {
        this.updateConfirmButton();
        this.shineTimer -= Gdx.graphics.getDeltaTime();
        if (this.shineTimer < 0.0F && !Settings.DISABLE_EFFECTS)
        {
            this.shineTimer = 0.1F;
            AbstractDungeon.topLevelEffects.add(new BossChestShineEffect());
            AbstractDungeon.topLevelEffects.add(new BossChestShineEffect(MathUtils.random(0.0F, (float) Settings.WIDTH), MathUtils.random(0.0F, (float) Settings.HEIGHT - 128.0F * Settings.scale)));
        }
        
        this.updateControllerInput();
        Iterator var1;
        if (AbstractDungeon.actNum >= 4 && AbstractPlayer.customMods.contains("Blight Chests"))
        {
            var1 = this.blights.iterator();
            
            while (var1.hasNext())
            {
                AbstractBlight b = (AbstractBlight) var1.next();
                b.update();
                if (b.isObtained)
                {
                    this.blightObtainLogic(b);
                }
            }
        }
        else
        {
            var1 = this.relics.iterator();
            
            while (var1.hasNext())
            {
                AbstractRelic r = (AbstractRelic) var1.next();
                r.update();
                if (r.isObtained)
                {
                    this.relicObtainLogic(r);
                }
            }
        }
        
        if (this.isDone)
        {
            this.isDone = false;
            this.mayVote = false;
            this.updateVote();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            this.relics.clear();
            AbstractDungeon.closeCurrentScreen();
        }
        
        this.updateCancelButton();
    }
    
    private void updateControllerInput()
    {
        if (Settings.isControllerMode && !AbstractDungeon.topPanel.selectPotionMode && AbstractDungeon.topPanel.potionUi.isHidden && !AbstractDungeon.player.viewingRelics)
        {
            boolean anyHovered = false;
            int index = 0;
            
            Iterator var3;
            for (var3 = this.relics.iterator(); var3.hasNext(); ++index)
            {
                AbstractRelic r = (AbstractRelic) var3.next();
                if (r.hb.hovered)
                {
                    anyHovered = true;
                    break;
                }
            }
            
            for (var3 = this.blights.iterator(); var3.hasNext(); ++index)
            {
                AbstractBlight b = (AbstractBlight) var3.next();
                if (b.hb.hovered)
                {
                    anyHovered = true;
                    break;
                }
            }
            
            if (!anyHovered)
            {
                if (!this.relics.isEmpty())
                {
                    CInputHelper.setCursor(((AbstractRelic) this.relics.get(0)).hb);
                }
                else
                {
                    CInputHelper.setCursor(((AbstractBlight) this.blights.get(0)).hb);
                }
            }
            else if (!this.relics.isEmpty())
            {
                if (!CInputActionSet.left.isJustPressed() && !CInputActionSet.altLeft.isJustPressed())
                {
                    if (!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    {
                        if (!CInputActionSet.up.isJustPressed() && !CInputActionSet.altUp.isJustPressed())
                        {
                            if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && index == 0)
                            {
                                CInputHelper.setCursor(((AbstractRelic) this.relics.get(1)).hb);
                            }
                        }
                        else if (index != 0)
                        {
                            CInputHelper.setCursor(((AbstractRelic) this.relics.get(0)).hb);
                        }
                    }
                    else if (index != 2)
                    {
                        CInputHelper.setCursor(((AbstractRelic) this.relics.get(2)).hb);
                    }
                }
                else if (index != 1)
                {
                    CInputHelper.setCursor(((AbstractRelic) this.relics.get(1)).hb);
                }
            }
            else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                if (index == 0)
                {
                    CInputHelper.setCursor(((AbstractBlight) this.blights.get(1)).hb);
                }
                else
                {
                    CInputHelper.setCursor(((AbstractBlight) this.blights.get(0)).hb);
                }
            }
            
        }
    }
    
    private void blightObtainLogic(AbstractBlight b)
    {
        HashMap<String, Object> choice = new HashMap();
        ArrayList<String> notPicked = new ArrayList();
        choice.put("picked", b.blightID);
        TreasureRoomBoss curRoom = (TreasureRoomBoss) AbstractDungeon.getCurrRoom();
        curRoom.choseRelic = true;
        Iterator var5 = this.blights.iterator();
        
        while (var5.hasNext())
        {
            AbstractBlight otherBlights = (AbstractBlight) var5.next();
            if (otherBlights != b)
            {
                notPicked.add(otherBlights.blightID);
            }
        }
        
        choice.put("not_picked", notPicked);
        CardCrawlGame.metricData.boss_relics.add(choice);
        this.isDone = true;
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 99999.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.getCurrRoom().phase = RoomPhase.COMPLETE;
    }
    
    private void relicObtainLogic(AbstractRelic r)
    {
        HashMap<String, Object> choice = new HashMap();
        ArrayList<String> notPicked = new ArrayList();
        choice.put("picked", r.relicId);
        TreasureRoomBoss curRoom = (TreasureRoomBoss) AbstractDungeon.getCurrRoom();
        curRoom.choseRelic = true;
        Iterator var5 = this.relics.iterator();
        
        while (var5.hasNext())
        {
            AbstractRelic otherRelics = (AbstractRelic) var5.next();
            if (otherRelics != r)
            {
                notPicked.add(otherRelics.relicId);
            }
        }
        
        choice.put("not_picked", notPicked);
        
        //remove the boss relics and the chosen relic, but leave any rare relics in the pool
        for (AbstractRelic rel : relics)
        {
            if ((rel.tier == AbstractRelic.RelicTier.BOSS) ||
                        (rel.relicId.equals(r.relicId)))
            {
                BcUtility.removeRelicFromDungeon(rel.relicId);
            }
        }
        
        CardCrawlGame.metricData.boss_relics.add(choice);
        this.isDone = true;
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 99999.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        if (r.relicId.equals("Black Blood") || r.relicId.equals("Ring of the Serpent") || r.relicId.equals("FrozenCore") || r.relicId.equals("HolyWater"))
        {
            r.instantObtain(AbstractDungeon.player, 0, true);
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }
        
    }
    
    private void relicSkipLogic()
    {
        if (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss && AbstractDungeon.screen == CurrentScreen.BOSS_REWARD)
        {
            TreasureRoomBoss r = (TreasureRoomBoss) AbstractDungeon.getCurrRoom();
            r.chest.close();
        }
        
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.mayVote = false;
        this.updateVote();
    }
    
    private void updateCancelButton()
    {
        this.cancelButton.update();
        if (this.cancelButton.hb.clicked)
        {
            this.cancelButton.hb.clicked = false;
            this.relicSkipLogic();
        }
        
    }
    
    private void updateConfirmButton()
    {
        this.confirmButton.update();
        if (this.confirmButton.hb.clicked)
        {
            this.confirmButton.hb.clicked = false;
            if (this.touchRelic != null)
            {
                this.touchRelic.bossObtainLogic();
            }
            
            if (this.touchBlight != null)
            {
                this.touchBlight.bossObtainLogic();
            }
        }
        
        if (InputHelper.justReleasedClickLeft && (this.touchRelic != null || this.touchBlight != null))
        {
            this.touchRelic = null;
            this.touchBlight = null;
            this.confirmButton.hide();
        }
        
    }
    
    public void noPick()
    {
        ArrayList<String> notPicked = new ArrayList();
        HashMap<String, Object> choice = new HashMap();
        Iterator var3 = this.relics.iterator();
        
        while (var3.hasNext())
        {
            AbstractRelic otherRelics = (AbstractRelic) var3.next();
            notPicked.add(otherRelics.relicId);
        }
        
        choice.put("not_picked", notPicked);
        CardCrawlGame.metricData.boss_relics.add(choice);
    }
    
    public void render(SpriteBatch sb)
    {
        Iterator var2 = AbstractDungeon.effectList.iterator();
        
        while (var2.hasNext())
        {
            AbstractGameEffect e = (AbstractGameEffect) var2.next();
            e.render(sb);
        }
        
        this.cancelButton.render(sb);
        this.confirmButton.render(sb);
        ((TreasureRoomBoss) AbstractDungeon.getCurrRoom()).chest.render(sb);
        AbstractDungeon.player.render(sb);
        sb.setColor(Color.WHITE);
        sb.draw(this.smokeImg, (float) Settings.WIDTH / 2.0F - 490.0F * Settings.scale, AbstractDungeon.floorY - 58.0F * Settings.scale, (float) this.smokeImg.getWidth() * Settings.scale, (float) this.smokeImg.getHeight() * Settings.scale);
        var2 = this.relics.iterator();
        
        while (var2.hasNext())
        {
            AbstractRelic r = (AbstractRelic) var2.next();
            r.render(sb);
        }
        
        var2 = this.blights.iterator();
        
        while (var2.hasNext())
        {
            AbstractBlight b = (AbstractBlight) var2.next();
            b.render(sb);
        }
        
        if (AbstractDungeon.topPanel.twitch.isPresent())
        {
            this.renderTwitchVotes(sb);
        }
        
    }
    
    private void renderTwitchVotes(SpriteBatch sb)
    {
        if (this.isVoting)
        {
            if (this.getVoter().isPresent())
            {
                TwitchVoter twitchVoter = (TwitchVoter) this.getVoter().get();
                TwitchVoteOption[] options = twitchVoter.getOptions();
                int sum = (Integer) Arrays.stream(options).map((c) ->
                {
                    return c.voteCount;
                }).reduce(0, Integer::sum);
                
                for (int i = 0; i < this.relics.size(); ++i)
                {
                    String s = "#" + (i + 1) + ": " + options[i + 1].voteCount;
                    if (sum > 0)
                    {
                        s = s + " (" + options[i + 1].voteCount * 100 / sum + "%)";
                    }
                    
                    switch (i)
                    {
                        case 0:
                            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, s, SLOT_1_X, SLOT_1_Y - 75.0F * Settings.scale, Color.WHITE);
                            break;
                        case 1:
                            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, s, SLOT_2_X, SLOT_2_Y - 75.0F * Settings.scale, Color.WHITE);
                            break;
                        case 2:
                            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, s, SLOT_3_X, SLOT_2_Y - 75.0F * Settings.scale, Color.WHITE);
                    }
                }
                
                String s = "#0: " + options[0].voteCount;
                if (sum > 0)
                {
                    s = s + " (" + options[0].voteCount * 100 / sum + "%)";
                }
                
                FontHelper.renderFont(sb, FontHelper.panelNameFont, s, 20.0F, 256.0F * Settings.scale, Color.WHITE);
                FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[4] + twitchVoter.getSecondsRemaining() + TEXT[5], (float) Settings.WIDTH / 2.0F, 192.0F * Settings.scale, Color.WHITE);
            }
            
        }
    }
    
    public void reopen()
    {
        this.confirmButton.hideInstantly();
        this.touchRelic = null;
        this.touchBlight = null;
        this.refresh();
        this.cancelButton.show(TEXT[3]);
        AbstractDungeon.dynamicBanner.appearInstantly(BANNER_Y, SELECT_MSG);
        AbstractDungeon.screen = CurrentScreen.BOSS_REWARD;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
    }
    
    public void openBlight(ArrayList<AbstractBlight> chosenBlights)
    {
        this.confirmButton.hideInstantly();
        this.touchRelic = null;
        this.touchBlight = null;
        this.refresh();
        this.blights.clear();
        AbstractDungeon.dynamicBanner.appear(BANNER_Y, TEXT[6]);
        this.smokeImg = ImageMaster.BOSS_CHEST_SMOKE;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = CurrentScreen.BOSS_REWARD;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractBlight r2 = (AbstractBlight) chosenBlights.get(0);
        r2.spawn(this.B_SLOT_1_X, this.B_SLOT_1_Y);
        r2.hb.move(r2.currentX, r2.currentY);
        this.blights.add(r2);
        AbstractBlight r3 = (AbstractBlight) chosenBlights.get(1);
        r3.spawn(this.B_SLOT_2_X, this.B_SLOT_1_Y);
        r3.hb.move(r3.currentX, r3.currentY);
        this.blights.add(r3);
    }
    
    public void open(ArrayList<AbstractRelic> chosenRelics)
    {
        this.confirmButton.hideInstantly();
        this.touchRelic = null;
        this.touchBlight = null;
        this.refresh();
        this.relics.clear();
        this.blights.clear();
        this.cancelButton.show(TEXT[3]);
        AbstractDungeon.dynamicBanner.appear(BANNER_Y, SELECT_MSG);
        this.smokeImg = ImageMaster.BOSS_CHEST_SMOKE;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = CurrentScreen.BOSS_REWARD;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        
        AbstractRelic r = (AbstractRelic) chosenRelics.get(0);
        r.spawn(SLOT_1_X, SLOT_1_Y);
        r.hb.move(r.currentX, r.currentY);
        this.relics.add(r);
        
        AbstractRelic r2 = (AbstractRelic) chosenRelics.get(1);
        r2.spawn(SLOT_2_X, SLOT_2_Y);
        r2.hb.move(r2.currentX, r2.currentY);
        this.relics.add(r2);
        
        AbstractRelic r3 = (AbstractRelic) chosenRelics.get(2);
        r3.spawn(SLOT_3_X, SLOT_3_Y);
        r3.hb.move(r3.currentX, r3.currentY);
        this.relics.add(r3);
        
        if (chosenRelics.size() > 3)
        {
            AbstractRelic r4 = (AbstractRelic) chosenRelics.get(3);
            r4.spawn(SLOT_4_X, SLOT_4_Y);
            r4.hb.move(r4.currentX, r4.currentY);
            this.relics.add(r4);
        }
        
        if (chosenRelics.size() > 4)
        {
            AbstractRelic r5 = (AbstractRelic) chosenRelics.get(4);
            r5.spawn(SLOT_5_X, SLOT_5_Y);
            r5.hb.move(r5.currentX, r5.currentY);
            this.relics.add(r5);
        }
        
        Iterator var5 = this.relics.iterator();
        
        while (var5.hasNext())
        {
            AbstractRelic r1 = (AbstractRelic) var5.next();
            //only mark boss relics as seen. dont want to punish players by excluding rare relics from future picks.
            if (r1.tier == AbstractRelic.RelicTier.BOSS)
            {
                UnlockTracker.markRelicAsSeen(r1.relicId);
            }
        }
        
        this.mayVote = true;
        this.updateVote();
    }
    
    public void refresh()
    {
        this.isDone = false;
        this.cancelButton = new MenuCancelButton();
        this.shineTimer = 0.0F;
    }
    
    public void hide()
    {
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.overlayMenu.cancelButton.hide();
    }
    
    private Optional<TwitchVoter> getVoter()
    {
        return TwitchPanel.getDefaultVoter();
    }
    
    private void updateVote()
    {
        if (this.getVoter().isPresent())
        {
            TwitchVoter twitchVoter = (TwitchVoter) this.getVoter().get();
            if (this.mayVote && twitchVoter.isVotingConnected() && !this.isVoting)
            {
                logger.info("Publishing Boss Relic Vote");
                this.isVoting = twitchVoter.initiateSimpleNumberVote((String[]) Stream.concat(Stream.of("skip"), this.relics.stream().map(AbstractRelic::toString)).toArray((x$0) ->
                {
                    return new String[x$0];
                }), this::completeVoting);
            }
            else if (this.isVoting && (!this.mayVote || !twitchVoter.isVotingConnected()))
            {
                twitchVoter.endVoting(true);
                this.isVoting = false;
            }
        }
        
    }
    
    public void completeVoting(int option)
    {
        if (this.isVoting)
        {
            this.isVoting = false;
            if (this.getVoter().isPresent())
            {
                TwitchVoter twitchVoter = (TwitchVoter) this.getVoter().get();
                AbstractDungeon.topPanel.twitch.ifPresent((twitchPanel) ->
                {
                    twitchPanel.connection.sendMessage("Voting on relic ended... chose " + twitchVoter.getOptions()[option].displayName);
                });
            }
            
            while (AbstractDungeon.screen != CurrentScreen.BOSS_REWARD)
            {
                AbstractDungeon.closeCurrentScreen();
            }
            
            if (option == 0)
            {
                this.relicSkipLogic();
            }
            else if (option < this.relics.size() + 1)
            {
                AbstractRelic r = (AbstractRelic) this.relics.get(option - 1);
                if (!r.relicId.equals("Black Blood") && !r.relicId.equals("Ring of the Serpent"))
                {
                    r.obtain();
                }
                
                r.isObtained = true;
            }
            
        }
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("BossRelicSelectScreen");
        TEXT = uiStrings.TEXT;
        SELECT_MSG = TEXT[2];
        BANNER_Y = AbstractDungeon.floorY + 460.0F * Settings.scale;
        
        SLOT_1_X = (float) Settings.WIDTH / 2.0F + 4.0F * Settings.scale;
        SLOT_1_Y = AbstractDungeon.floorY + 380.0F * Settings.scale;
        
        SLOT_2_X = (float) Settings.WIDTH / 2.0F - 116.0F * Settings.scale;
        SLOT_2_Y = AbstractDungeon.floorY + 220.0F * Settings.scale;
        
        SLOT_3_X = (float) Settings.WIDTH / 2.0F + 124.0F * Settings.scale;
        SLOT_3_Y = SLOT_2_Y;
        
        SLOT_4_X = (float) Settings.WIDTH / 2.0F - 170.0F * Settings.scale;
        SLOT_4_Y = AbstractDungeon.floorY + 370.0F * Settings.scale;
        ;
        
        SLOT_5_X = (float) Settings.WIDTH / 2.0F + 170.0F * Settings.scale;
        SLOT_5_Y = SLOT_4_Y;
        
        TwitchVoter.registerListener(new TwitchVoteListener()
        {
            public void onTwitchAvailable()
            {
                AbstractDungeon.bossRelicScreen.updateVote();
            }
            
            public void onTwitchUnavailable()
            {
                AbstractDungeon.bossRelicScreen.updateVote();
            }
        });
    }
}
