package dev.custom.portals.blocks;

import dev.custom.portals.config.CPSettings;
import dev.custom.portals.data.CustomPortal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.util.EntityMixinAccess;
import dev.custom.portals.registry.CPItems;
import dev.custom.portals.registry.CPParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ScheduledTickAccess;
import org.jetbrains.annotations.Nullable;

public class PortalBlock extends Block implements EntityBlock, SimpleWaterloggedBlock, Portal {
   public static final BooleanProperty LIT;
   public static final BooleanProperty WATERLOGGED;
   public static final EnumProperty<Direction.Axis> AXIS;
   protected static final VoxelShape X_SHAPE;
   protected static final VoxelShape Z_SHAPE;
   protected static final VoxelShape Y_SHAPE;
    
   public PortalBlock(BlockBehaviour.Properties settings) {
      super(settings);
      this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(LIT, false).setValue(WATERLOGGED, false));
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      switch(state.getValue(AXIS)) {
      case Z:
         return Z_SHAPE;
      case Y:
         return Y_SHAPE;
      case X:
      default:
         return X_SHAPE;
      }
   }

   @Override
   public InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level world, BlockPos blockPos, Player playerEntity, InteractionHand hand, BlockHitResult blockHitResult) {
      if (playerEntity.isShiftKeyDown() && itemStack.isEmpty()) {
         CustomPortal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(blockPos);
         if (portal == null) return InteractionResult.FAIL;
         portal.setSpawnPos(blockPos);
         if (world.isClientSide())
            playerEntity.displayClientMessage(Component.nullToEmpty("Set portal's spawn position to " + CustomPortals.blockPosToString(blockPos)), true);
         return InteractionResult.SUCCESS;
      }
      return InteractionResult.TRY_WITH_EMPTY_HAND;
   }
   @Override
   public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
      CustomPortal portal = CustomPortals.PORTALS.get(serverLevel).getPortalFromPos(pos);
      if(portal == null)
         return;
      if(portal.isInterdimensional()) {
         if (portal.getLinked().getDimensionId().equals("minecraft:the_nether") && serverLevel.isSpawningMonsters() && random.nextInt(2000) < serverLevel.getDifficulty().getId()) {
            while(serverLevel.getBlockState(pos).is(this)) {
               pos = pos.below();
            }
  
            if (serverLevel.getBlockState(pos).isValidSpawn(serverLevel, pos, EntityType.ZOMBIFIED_PIGLIN)) {
               Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(serverLevel, pos.above(), EntitySpawnReason.STRUCTURE);
               if (entity != null) {
                  entity.setPortalCooldown();
               }
            }
         }
         if (portal.getLinked().getDimensionId().equals("minecraft:the_end") && serverLevel.isSpawningMonsters() && random.nextInt(2000) < serverLevel.getDifficulty().getId()) {
            while(serverLevel.getBlockState(pos).is(this)) {
               pos = pos.below();
            }
  
            if (serverLevel.getBlockState(pos).isValidSpawn(serverLevel, pos, EntityType.ENDERMAN)) {
               Entity entity = EntityType.ENDERMAN.spawn(serverLevel, pos.above(), EntitySpawnReason.STRUCTURE);
               if (entity != null) {
                  entity.setPortalCooldown();
               }
            }
         }
      }
   }

   @Override
   public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
      super.playerWillDestroy(world, pos, state, player);
      CustomPortal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(pos);
      if(portal != null) {
         CustomPortals.PORTALS.get(world).unregisterPortal(portal);
         if(!world.isClientSide())
            CustomPortals.PORTALS.get(world).syncWithAll(((ServerLevel)world).getServer());
      }
      return state;
   }

   private void dropCatalyst(CustomPortal portal, Level world) {
      Item catalyst;
      switch(this.defaultMapColor().id) {
         case 29: catalyst = CPItems.BLACK_PORTAL_CATALYST;
         break;
         case 25: catalyst = CPItems.BLUE_PORTAL_CATALYST;
         break;
         case 26: catalyst = CPItems.BROWN_PORTAL_CATALYST;
         break;
         case 23: catalyst = CPItems.CYAN_PORTAL_CATALYST;
         break;
         case 21: catalyst = CPItems.GRAY_PORTAL_CATALYST;
         break;
         case 27: catalyst = CPItems.GREEN_PORTAL_CATALYST;
         break;
         case 17: catalyst = CPItems.LIGHT_BLUE_PORTAL_CATALYST;
         break;
         case 22: catalyst = CPItems.LIGHT_GRAY_PORTAL_CATALYST;
         break;
         case 19: catalyst = CPItems.LIME_PORTAL_CATALYST;
         break;
         case 16: catalyst = CPItems.MAGENTA_PORTAL_CATALYST;
         break;
         case 15: catalyst = CPItems.ORANGE_PORTAL_CATALYST;
         break;
         case 20: catalyst = CPItems.PINK_PORTAL_CATALYST;
         break;
         case 24: catalyst = CPItems.PURPLE_PORTAL_CATALYST;
         break;
         case 28: catalyst = CPItems.RED_PORTAL_CATALYST;
         break;
         case 8: catalyst = CPItems.WHITE_PORTAL_CATALYST;
         break;
         case 18: 
         default: catalyst = CPItems.YELLOW_PORTAL_CATALYST;
      }
      ItemStack itemStack = new ItemStack(catalyst);
      Block.popResource(world, portal.getSpawnPos(), itemStack);
   }
   
   @Override
   public BlockState updateShape(BlockState state, LevelReader worldView, ScheduledTickAccess scheduledTickView, BlockPos pos, Direction direction, BlockPos posFrom, BlockState newState, RandomSource random) {
      Direction.Axis axis = direction.getAxis();
      Direction.Axis axis2 = state.getValue(AXIS);
      Level world = (Level)worldView;
      boolean bl = axis2 == Direction.Axis.Y ? axis2 == axis && axis.isVertical() : axis2 != axis && axis.isHorizontal();
      if(!bl && !newState.is(this) && !PortalShape.findAnyShape(worldView, pos, axis2).isComplete()) {
         CustomPortal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(pos);
         if(portal != null) {
            if (newState.getBlock().getDescriptionId().equals(portal.getFrameId()))
               return super.updateShape(state, worldView, scheduledTickView, pos, direction, posFrom, newState, random);
            CustomPortals.PORTALS.get(world).unregisterPortal(portal);
            if(!world.isClientSide())
               CustomPortals.PORTALS.get(world).syncWithAll(((ServerLevel)world).getServer());
            dropCatalyst(portal, world);
         }
         return Blocks.AIR.defaultBlockState();
      }
      return super.updateShape(state, worldView, scheduledTickView, pos, direction, posFrom, newState, random);
   }

   private boolean checkRedstoneSignal(Level world, CustomPortal portal) {
      for (BlockPos pos : portal.getPortalBlocks()) {
         if (world.hasNeighborSignal(pos))
            return true;
      }
      return false;
   }

   @Override
   public void neighborChanged(BlockState blockState, Level world, BlockPos blockPos, Block block, @Nullable Orientation wireOrientation, boolean bl) {
      CustomPortal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(blockPos);
      if (portal == null)
         return;
      boolean bl2 = checkRedstoneSignal(world, portal);
      if (bl2 != portal.hasRedstoneSignal()) {
         portal.setHasRedstoneSignal(bl2);
      }
   }

   @Override
   public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier entityCollisionHandler, boolean bl) {
      if (!state.getValue(LIT))
         return;
      CustomPortal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(pos);
      if(portal != null && entity.canUsePortal(false)) {
         entity.setAsInsidePortal(this, pos);
         ((EntityMixinAccess) entity).setInCustomPortal(portal);
      }
      // For debugging purposes
      /*if(portal != null) {
         if(portal.hasLinked())
            System.out.println("Linked Portal at " + portal.getLinked().getSpawnPos().getX() + ", " + portal.getLinked().getSpawnPos().getY() + ", " + portal.getLinked().getSpawnPos().getZ());
         else System.out.println("Portal is not linked!");
      } else System.out.println("No portal found!");*/
   }
   
   @Environment(EnvType.CLIENT)
   public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
      if (!(Boolean)state.getValue(LIT))
         return;
      if (!CPSettings.instance().muteAmbientSounds && random.nextInt(100) == 0) {
         world.playLocalSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
      }
      for(int i = 0; i < 4; ++i) {
         double d = (double)pos.getX() + random.nextDouble();
         double e = (double)pos.getY() + random.nextDouble();
         double f = (double)pos.getZ() + random.nextDouble();
         double g = ((double)random.nextFloat() - 0.5D) * 0.5D;
         double h = ((double)random.nextFloat() - 0.5D) * 0.5D;
         double j = ((double)random.nextFloat() - 0.5D) * 0.5D;
         int k = random.nextInt(2) * 2 - 1;
         if (!world.getBlockState(pos.west()).is(this) && !world.getBlockState(pos.east()).is(this) && (Direction.Axis)state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Z) {
            d = (double)pos.getX() + 0.5D + 0.25D * (double)k;
            g = (double)(random.nextFloat() * 2.0F * (float)k);
         } 
         else if (!world.getBlockState(pos.above()).is(this) && !world.getBlockState(pos.below()).is(this)) {
            e = (double)pos.getY() + 0.5D + 0.25D * (double)k;
            h = (double)(random.nextFloat() * 2.0F * (float)k);
         } else {
            f = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
            j = (double)(random.nextFloat() * 2.0F * (float)k);
         }
         switch(this.defaultMapColor().id) {
            case 29: world.addParticle(CPParticles.BLACK_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 25: world.addParticle(CPParticles.BLUE_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 26: world.addParticle(CPParticles.BROWN_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 23: world.addParticle(CPParticles.CYAN_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 21: world.addParticle(CPParticles.GRAY_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 27: world.addParticle(CPParticles.GREEN_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 17: world.addParticle(CPParticles.LIGHT_BLUE_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 22: world.addParticle(CPParticles.LIGHT_GRAY_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 19: world.addParticle(CPParticles.LIME_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 16: world.addParticle(CPParticles.MAGENTA_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 15: world.addParticle(CPParticles.ORANGE_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 20: world.addParticle(CPParticles.PINK_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 24: world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
            break;
            case 28: world.addParticle(CPParticles.RED_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 8: world.addParticle(CPParticles.WHITE_PORTAL_PARTICLE, d, e, f, g, h, j);
            break;
            case 18: world.addParticle(CPParticles.YELLOW_PORTAL_PARTICLE, d, e, f, g, h, j);
         }
      }
   
   }

   @Override
   public boolean canPlaceLiquid(@Nullable LivingEntity livingEntity, BlockGetter blockView, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      return false;
   }

   @Override
   protected boolean canBeReplaced(BlockState blockState, Fluid fluid) {
      return false;
   }

   @Environment(EnvType.CLIENT)
   public ItemStack getPickStack(BlockGetter world, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }
   
   @Override
   public BlockState rotate(BlockState state, Rotation rotation) {
      switch(rotation) {
         // use COUNTERCLOCKWISE_90 to rotate around Y axis
         case COUNTERCLOCKWISE_90:
            switch((Direction.Axis)state.getValue(AXIS)) {
               case Z:
               case X:
                  return (BlockState)state.setValue(AXIS, Direction.Axis.Y);
               case Y:
                  return (BlockState)state.setValue(AXIS, Direction.Axis.X);
               default:
                  return state;
            }
         case CLOCKWISE_90:
            switch((Direction.Axis)state.getValue(AXIS)) {
               case Z:
                  return (BlockState)state.setValue(AXIS, Direction.Axis.X);
               case X:
               case Y:
                  return (BlockState)state.setValue(AXIS, Direction.Axis.Z);
               default:
                  return state;
            }
      default:
         return state;
      }
   }
  
   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(AXIS, LIT, WATERLOGGED);
   }

   static {
      LIT = BlockStateProperties.LIT;
      WATERLOGGED = BlockStateProperties.WATERLOGGED;
      AXIS = BlockStateProperties.AXIS;
      X_SHAPE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
      Z_SHAPE = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
      Y_SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new PortalBlockEntity(pos, state);
   }

   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
                                                                 BlockEntityType<T> type) {
      return PortalBlockEntity::tick;
   }

   @Nullable
   @Override
   public TeleportTransition getPortalDestination(ServerLevel serverWorld, Entity entity, BlockPos blockPos) {
      CustomPortal portal = CustomPortals.PORTALS.get(serverWorld).getPortalFromPos(blockPos);
      if (portal == null) return null;
      CustomPortal destPortal = portal.getLinked();
      if (destPortal == null) return null;
      MinecraftServer minecraftServer = serverWorld.getServer();
      String dimensionId = portal.getDimensionId();
      String destDimensionId = destPortal.getDimensionId();
      ServerLevel serverWorld2 = null;
      if(!destDimensionId.equals(dimensionId)) {
         for(ResourceKey<Level> registryKey : minecraftServer.levelKeys()) {
            if(registryKey.identifier().toString().equals(destDimensionId)) {
               serverWorld2 = minecraftServer.getLevel(registryKey);
            }
         }
      }
      else serverWorld2 = serverWorld;
      if (serverWorld2 == null) {
         return null;
      } else {
         BlockPos dest = destPortal.getSpawnPos();
         float destX = (float)dest.getX();
         float destY = (float)dest.getY();
         float destZ = (float)dest.getZ();
         destX += destPortal.offsetX;
         destZ += destPortal.offsetZ;
         /* For some reason, when the player is going from the Overworld to the End, the Y coordinate somehow gets
          * decreased by 1. I have no idea why this happens or how to fix it directly, so this is here to correct it.
          */
         if(destPortal.getDimensionId().equals("minecraft:the_end") && serverWorld2.dimension() == Level.OVERWORLD)
            destY += 1.0f;
         return new TeleportTransition(serverWorld2, new Vec3(destX, destY, destZ), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), TeleportTransition.DO_NOTHING);
      }
   }

   @Override
   public int getPortalTransitionTime(ServerLevel serverLevel, Entity entity) {
      CustomPortal destPortal = ((EntityMixinAccess)entity).getDestPortal();
      if (entity instanceof Player playerEntity && destPortal != null) {
         if (CPSettings.instance().alwaysHaste == CPSettings.HasteEnum.CREATIVE) {
            return Math.max(1, playerEntity.getAbilities().invulnerable ? serverLevel.getGameRules().get(GameRules.PLAYERS_NETHER_PORTAL_CREATIVE_DELAY) : destPortal.getPlayerTeleportDelay());
         }
         else return destPortal.getPlayerTeleportDelay();
      }
      return 0;
   }
}