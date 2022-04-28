//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.GainBlockRandomMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import java.util.Iterator;

public class Centurion extends AbstractMonster {
    public static final String ID = "Centurion";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final float IDLE_TIMESCALE = 0.8F;
    private static final int HP_MIN = 76;
    private static final int HP_MAX = 80;
    private static final int A_2_HP_MIN = 109;
    private static final int A_2_HP_MAX = 122;
    private static final int SLASH_DMG = 12;
    private static final int FURY_DMG = 6;
    private static final int FURY_HITS = 3;
    private static final int A_2_SLASH_DMG = 14;
    private static final int A_2_FURY_DMG = 7;
    private int slashDmg;
    private int furyDmg;
    private int furyHits;
    private int blockAmount;
    private int BLOCK_AMOUNT = 15;
    private int A_17_BLOCK_AMOUNT = 20;
    private static final byte SLASH = 1;
    private static final byte PROTECT = 2;
    private static final byte FURY = 3;

    public Centurion(float x, float y) {
        super(NAME, "Centurion", 80, -14.0F, -20.0F, 250.0F, 330.0F, (String)null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(159, 182);
        } else {
            this.setHp(108, 120);
        }

        if (AbstractDungeon.ascensionLevel >= 17) {
            this.blockAmount = this.A_17_BLOCK_AMOUNT;
        } else {
            this.blockAmount = this.BLOCK_AMOUNT;
        }

        if (AbstractDungeon.ascensionLevel >= 17)
        {
            this.slashDmg = 16;
            this.furyDmg = 8;
            this.furyHits = 3;
        }
        else if (AbstractDungeon.ascensionLevel >= 2)
        {
            this.slashDmg = 14;
            this.furyDmg = 7;
            this.furyHits = 3;
        }
        else
        {
            this.slashDmg = 12;
            this.furyDmg = 6;
            this.furyHits = 3;
        }

        //start damaged, so the mystic will immediately heal him
        this.currentHealth = this.maxHealth - 22;

        this.damage.add(new DamageInfo(this, this.slashDmg));
        this.damage.add(new DamageInfo(this, this.furyDmg));
        this.loadAnimation("images/monsters/theCity/tank/skeleton.atlas", "images/monsters/theCity/tank/skeleton.json", 1.0F);
        TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2F);
        this.state.setTimeScale(0.8F);
    }

    public void takeTurn() {
        switch(this.nextMove) {
            case 1:
                this.playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MACE_HIT"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(0), AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                AbstractDungeon.actionManager.addToBottom(new GainBlockRandomMonsterAction(this, this.blockAmount));
                break;
            case 3:
                for(int i = 0; i < this.furyHits; ++i) {
                    this.playSfx();
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MACE_HIT"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)this.damage.get(1), AttackEffect.BLUNT_HEAVY));
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx() {
        int roll = MathUtils.random(1);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_TANK_1C"));
        }

    }

    public void changeState(String key) {
        byte var3 = -1;
        switch(key.hashCode()) {
            case 595384490:
                if (key.equals("MACE_HIT")) {
                    var3 = 0;
                }
            default:
                switch(var3) {
                    case 0:
                        this.state.setAnimation(0, "Attack", false);
                        this.state.addAnimation(0, "Idle", true, 0.0F);
                    default:
                }
        }
    }

    protected void getMove(int num)
    {
        int aliveCount = 0;
        AbstractMonster m;
        Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();
        while(var3.hasNext()) {
            m = (AbstractMonster)var3.next();
            if (!m.isDying && !m.isEscaping) {
                ++aliveCount;
            }
        }

        //always does big attack when mystic is dead
        if (aliveCount == 1)
        {
            this.setMove((byte)3, Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, this.furyHits, true);
        }
        else if (num >= 65 && !this.lastTwoMoves((byte)2) && !this.lastTwoMoves((byte)3))
        {
            this.setMove((byte)2, Intent.DEFEND);
        }
        else if (!this.lastTwoMoves((byte)1))
        {
            this.setMove((byte)1, Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        }
        else
        {
            this.setMove((byte)2, Intent.DEFEND);
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.setTimeScale(0.8F);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }

    }

    public void die() {
        this.state.setTimeScale(0.1F);
        this.useShakeAnimation(5.0F);
        super.die();
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Centurion");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
