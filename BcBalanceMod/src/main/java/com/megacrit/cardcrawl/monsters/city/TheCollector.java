//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.GlowyFireEyesEffect;
import com.megacrit.cardcrawl.vfx.StaffFireEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheCollector extends AbstractMonster {
    private static final Logger logger = LogManager.getLogger(TheCollector.class.getName());
    public static final String ID = "TheCollector";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public static final int HP = 282;
    public static final int A_2_HP = 300;
    private static final int FIREBALL_DMG = 18;
    private static final int STR_AMT = 3;
    private static final int BLOCK_AMT = 15;
    private static final int A_2_FIREBALL_DMG = 21;
    private static final int A_2_STR_AMT = 4;
    private static final int A_2_BLOCK_AMT = 18;
    private int rakeDmg;
    private int strAmt;
    private int blockAmt;
    private int megaDebuffAmt;
    private static final int MEGA_DEBUFF_AMT = 3;
    private int turnsTaken = 0;
    private float spawnX = -100.0F;
    private float fireTimer = 0.0F;
    private static final float FIRE_TIME = 0.07F;
    private boolean ultUsed = false;
    private boolean initialSpawn = true;
    private HashMap<Integer, AbstractMonster> enemySlots = new HashMap();
    private static final byte SPAWN = 1;
    private static final byte FIREBALL = 2;
    private static final byte BUFF = 3;
    private static final byte MEGA_DEBUFF = 4;
    private static final byte REVIVE = 5;
    
    public TheCollector() {
        super(NAME, "TheCollector", 282, 15.0F, -40.0F, 300.0F, 390.0F, (String)null, 60.0F, 135.0F);
        dialogX = -90.0F * Settings.scale;
        dialogY = 10.0F * Settings.scale;
        type = EnemyType.BOSS;
        if (AbstractDungeon.ascensionLevel >= 9) {
            //setHp(300);
            setHp(360);
            blockAmt = 18;
        } else {
            setHp(282);
            blockAmt = 15;
        }
        
        if (AbstractDungeon.ascensionLevel >= 19) {
            rakeDmg = 21;
            strAmt = 5;
            megaDebuffAmt = 5;
        } else if (AbstractDungeon.ascensionLevel >= 4) {
            rakeDmg = 21;
            strAmt = 4;
            megaDebuffAmt = 3;
        } else {
            rakeDmg = 18;
            strAmt = 3;
            megaDebuffAmt = 3;
        }
        
        damage.add(new DamageInfo(this, rakeDmg));
        loadAnimation("images/monsters/theCity/collector/skeleton.atlas", "images/monsters/theCity/collector/skeleton.json", 1.0F);
        TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }
    
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        UnlockTracker.markBossAsSeen("COLLECTOR");
    }
    
    public void takeTurn() {
        int key;
        TorchHead newMonster;
label52:
        switch(nextMove) {
            case 1:
                for(key = 1; key < 3; ++key) {
                    newMonster = new TorchHead(spawnX + -185.0F * (float)key, MathUtils.random(-5.0F, 25.0F));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_SUMMON"));
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(newMonster, true));
                    enemySlots.put(key, newMonster);
                }
                
                initialSpawn = false;
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), AttackEffect.FIRE));
                break;
            case 3:
                if (AbstractDungeon.ascensionLevel >= 19) {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmt + 5));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmt));
                }
                
                Iterator var5 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
                
                while(true) {
                    if (!var5.hasNext()) {
                        break label52;
                    }
                    
                    AbstractMonster m = (AbstractMonster)var5.next();
                    if (!m.isDead && !m.isDying && !m.isEscaping) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, strAmt), strAmt));
                    }
                }
            case 4:
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0]));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, megaDebuffAmt, true), megaDebuffAmt));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, megaDebuffAmt, true), megaDebuffAmt));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, megaDebuffAmt, true), megaDebuffAmt));
                ultUsed = true;
                break;
            case 5:
                Iterator var3 = enemySlots.entrySet().iterator();
                
                while(true) {
                    if (!var3.hasNext()) {
                        break label52;
                    }
                    
                    Entry<Integer, AbstractMonster> m = (Entry)var3.next();
                    if (((AbstractMonster)m.getValue()).isDying) {
                        newMonster = new TorchHead(spawnX + -185.0F * (float)(Integer)m.getKey(), MathUtils.random(-5.0F, 25.0F));
                        key = (Integer)m.getKey();
                        enemySlots.put(key, newMonster);
                        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(newMonster, true));
                    }
                }
            default:
                logger.info("ERROR: Default Take Turn was called on " + name);
        }
        
        ++turnsTaken;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    protected void getMove(int num) {
        if (initialSpawn) {
            setMove((byte)1, Intent.UNKNOWN);
        } else if (turnsTaken >= 3 && !ultUsed) {
            setMove((byte)4, Intent.STRONG_DEBUFF);
        } else if (num <= 25 && isMinionDead() && !lastMove((byte)5)) {
            setMove((byte)5, Intent.UNKNOWN);
        } else if (num <= 70 && !lastTwoMoves((byte)2)) {
            setMove((byte)2, Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
        } else {
            if (!lastMove((byte)3)) {
                setMove((byte)3, Intent.DEFEND_BUFF);
            } else {
                setMove((byte)2, Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            }
            
        }
    }
    
    private boolean isMinionDead() {
        Iterator var1 = enemySlots.entrySet().iterator();
        
        Entry m;
        do {
            if (!var1.hasNext()) {
                return false;
            }
            
            m = (Entry)var1.next();
        } while(!((AbstractMonster)m.getValue()).isDying);
        
        return true;
    }
    
    public void update() {
        super.update();
        if (!isDying) {
            fireTimer -= Gdx.graphics.getDeltaTime();
            if (fireTimer < 0.0F) {
                fireTimer = 0.07F;
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(skeleton.getX() + skeleton.findBone("lefteyefireslot").getX(), skeleton.getY() + skeleton.findBone("lefteyefireslot").getY() + 140.0F * Settings.scale));
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(skeleton.getX() + skeleton.findBone("righteyefireslot").getX(), skeleton.getY() + skeleton.findBone("righteyefireslot").getY() + 140.0F * Settings.scale));
                AbstractDungeon.effectList.add(new StaffFireEffect(skeleton.getX() + skeleton.findBone("fireslot").getX() - 120.0F * Settings.scale, skeleton.getY() + skeleton.findBone("fireslot").getY() + 390.0F * Settings.scale));
            }
        }
        
    }
    
    public void die() {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        ++deathTimer;
        super.die();
        onBossVictoryLogic();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        
        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (!m.isDead && !m.isDying) {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        }
        
        UnlockTracker.hardUnlockOverride("COLLECTOR");
        UnlockTracker.unlockAchievement("COLLECTOR");
    }
    
    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TheCollector");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
