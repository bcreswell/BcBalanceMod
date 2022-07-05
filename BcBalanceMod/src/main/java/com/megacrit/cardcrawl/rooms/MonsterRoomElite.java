package com.megacrit.cardcrawl.rooms;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import java.util.Iterator;

public class MonsterRoomElite extends MonsterRoom {
    public MonsterRoomElite() {
        this.mapSymbol = "E";
        this.mapImg = ImageMaster.MAP_NODE_ELITE;
        this.mapImgOutline = ImageMaster.MAP_NODE_ELITE_OUTLINE;
        this.eliteTrigger = true;
        this.baseRareCardChance = 9;
        this.baseUncommonCardChance = 40;
    }
    
    public void applyEmeraldEliteBuff() {
        if (Settings.isFinalActAvailable && AbstractDungeon.getCurrMapNode().hasEmeraldKey) {
            Iterator var1;
            AbstractMonster m;
            switch(AbstractDungeon.mapRng.random(0, 3)) {
                case 0:
                    var1 = this.monsters.monsters.iterator();
                    
                    while(var1.hasNext()) {
                        m = (AbstractMonster)var1.next();
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, AbstractDungeon.actNum + 1), AbstractDungeon.actNum + 1));
                    }
                    
                    return;
                case 1:
                    var1 = this.monsters.monsters.iterator();
                    
                    while(var1.hasNext()) {
                        m = (AbstractMonster)var1.next();
                        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(m, 0.25F, true));
                    }
                    
                    return;
                case 2:
                    var1 = this.monsters.monsters.iterator();
                    
                    while(var1.hasNext()) {
                        m = (AbstractMonster)var1.next();
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MetallicizePower(m, AbstractDungeon.actNum * 2 + 2), AbstractDungeon.actNum * 2 + 2));
                    }
                    
                    return;
                case 3:
                    var1 = this.monsters.monsters.iterator();
                    
                    while(var1.hasNext()) {
                        m = (AbstractMonster)var1.next();
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, 1 + AbstractDungeon.actNum * 2), 1 + AbstractDungeon.actNum * 2));
                    }
            }
        }
        
    }
    
    public void onPlayerEntry() {
        this.playBGM((String)null);
        if (this.monsters == null) {
            this.monsters = CardCrawlGame.dungeon.getEliteMonsterForRoomCreation();
            this.monsters.init();
        }
        
        waitTimer = 0.1F;
    }
    
    public void dropReward() {
        AbstractRelic.RelicTier tier = this.returnRandomRelicTier();
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("MimicInfestation")) {
            AbstractDungeon.player.getBlight("MimicInfestation").flash();
        } else {
            this.addRelicToRewards(tier);
            if (AbstractDungeon.player.hasRelic("Black Star")) {
                this.addNoncampRelicToRewards(this.returnRandomRelicTier());
            }
            
            this.addEmeraldKey();
        }
        
    }
    
    private void addEmeraldKey() {
        if (Settings.isFinalActAvailable && !Settings.hasEmeraldKey && !this.rewards.isEmpty() && AbstractDungeon.getCurrMapNode().hasEmeraldKey) {
            this.rewards.add(new RewardItem((RewardItem)this.rewards.get(this.rewards.size() - 1), RewardItem.RewardType.EMERALD_KEY));
        }
        
    }
    
    private AbstractRelic.RelicTier returnRandomRelicTier() {
        int roll = AbstractDungeon.relicRng.random(0, 99);
        if (ModHelper.isModEnabled("Elite Swarm")) {
            roll += 10;
        }
        
        if (roll < 50) {
            return AbstractRelic.RelicTier.COMMON;
        } else {
            return roll > 82 ? AbstractRelic.RelicTier.RARE : AbstractRelic.RelicTier.UNCOMMON;
        }
    }
    
    public AbstractCard.CardRarity getCardRarity(int roll) {
        return ModHelper.isModEnabled("Elite Swarm") ? AbstractCard.CardRarity.RARE : super.getCardRarity(roll);
    }
}
