package dev.custom.portals.blocks;

import dev.custom.portals.config.CPSettings;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.random.Random;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.util.EntityMixinAccess;
import dev.custom.portals.data.Portal;
import dev.custom.portals.registry.CPItems;
import dev.custom.portals.registry.CPParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.NetherPortal;
import net.minecraft.server.world.ServerWorld;

public class PortalBlock extends Block implements BlockEntityProvider, Waterloggable {
   public static final BooleanProperty LIT;
   public static final BooleanProperty WATERLOGGED;
   public static final EnumProperty<Direction.Axis> AXIS;
   protected static final VoxelShape X_SHAPE;
   protected static final VoxelShape Z_SHAPE;
   protected static final VoxelShape Y_SHAPE;
    
   public PortalBlock(AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X).with(LIT, false).with(WATERLOGGED, false));
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      switch(state.get(AXIS)) {
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
   public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
      if (playerEntity.isSneaking() && playerEntity.getStackInHand(hand).isEmpty()) {
         Portal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(blockPos);
         if (portal == null) return ActionResult.FAIL;
         portal.setSpawnPos(blockPos);
         if (world.isClient)
            playerEntity.sendMessage(Text.of("Set portal's spawn position to " + CustomPortals.blockPosToString(blockPos)));
         return ActionResult.SUCCESS;
      }
      return ActionResult.PASS;
   }
   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      Portal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(pos);
      if(portal == null)
         return;
      if(portal.isInterdimensional()) {
         if (portal.getLinked().getDimensionId().equals("minecraft:the_nether") && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < world.getDifficulty().getId()) {
            while(world.getBlockState(pos).isOf(this)) {
               pos = pos.down();
            }
  
            if (world.getBlockState(pos).allowsSpawning(world, pos, EntityType.ZOMBIFIED_PIGLIN)) {
               Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(world, pos.up(), SpawnReason.STRUCTURE);
               if (entity != null) {
                  entity.resetPortalCooldown();
               }
            }
         }
         if (portal.getLinked().getDimensionId().equals("minecraft:the_end") && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < world.getDifficulty().getId()) {
            while(world.getBlockState(pos).isOf(this)) {
               pos = pos.down();
            }
  
            if (world.getBlockState(pos).allowsSpawning(world, pos, EntityType.ENDERMAN)) {
               Entity entity = EntityType.ENDERMAN.spawn(world, pos.up(), SpawnReason.STRUCTURE);
               if (entity != null) {
                  entity.resetPortalCooldown();
               }
            }
         }
      }
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      super.onBreak(world, pos, state, player);
      Portal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(pos);
      if(portal != null) {
         CustomPortals.PORTALS.get(world).unregisterPortal(portal);
         if(!world.isClient)
            CustomPortals.PORTALS.get(world).syncWithAll(((ServerWorld)world).getServer());
      }
   }

   private void dropCatalyst(Portal portal, World world) {
      Item catalyst;
      switch(this.getDefaultMapColor().id) {
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
      Block.dropStack(world, portal.getSpawnPos(), itemStack);
   }
   
   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      Direction.Axis axis = direction.getAxis();
      Direction.Axis axis2 = (Direction.Axis)state.get(AXIS);
      boolean bl = axis2 == Direction.Axis.Y ? axis2 == axis && axis.isVertical() : axis2 != axis && axis.isHorizontal();
      if(!bl && !newState.isOf(this) && !(new NetherPortal(world, pos, axis2)).wasAlreadyValid()) {
         Portal portal = CustomPortals.PORTALS.get((World)world).getPortalFromPos(pos);
         if(portal != null) {
            if (newState.getBlock().getTranslationKey().equals(portal.getFrameId()))
               return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
            CustomPortals.PORTALS.get((World)world).unregisterPortal(portal);
            if(!((World)world).isClient)
               CustomPortals.PORTALS.get((World)world).syncWithAll(((ServerWorld)world).getServer());
            dropCatalyst(portal, (World)world);
         }
         return Blocks.AIR.getDefaultState();
      }
      return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   private boolean checkRedstoneSignal(World world, Portal portal) {
      for (BlockPos pos : portal.getPortalBlocks()) {
         if (world.isReceivingRedstonePower(pos))
            return true;
      }
      return false;
   }

   @Override
   public void neighborUpdate(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
      Portal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(blockPos);
      if (portal == null)
         return;
      boolean bl2 = checkRedstoneSignal(world, portal);
      if (bl2 != portal.hasRedstoneSignal()) {
         portal.setHasRedstoneSignal(bl2);
      }
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!state.get(LIT))
         return;
      Portal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(pos);
      if(portal != null && entity.canUsePortals())
         ((EntityMixinAccess)entity).setInCustomPortal(portal);
      // For debugging purposes
      /*if(portal != null) {
         if(portal.hasLinked())
            System.out.println("Linked Portal at " + portal.getLinked().getSpawnPos().getX() + ", " + portal.getLinked().getSpawnPos().getY() + ", " + portal.getLinked().getSpawnPos().getZ());
         else System.out.println("Portal is not linked!");
      } else System.out.println("No portal found!");*/
   }
   
   @Environment(EnvType.CLIENT)
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (!(Boolean)state.get(LIT))
         return;
      if (!CPSettings.instance().muteAmbientSounds && random.nextInt(100) == 0) {
         world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
      }
      for(int i = 0; i < 4; ++i) {
         double d = (double)pos.getX() + random.nextDouble();
         double e = (double)pos.getY() + random.nextDouble();
         double f = (double)pos.getZ() + random.nextDouble();
         double g = ((double)random.nextFloat() - 0.5D) * 0.5D;
         double h = ((double)random.nextFloat() - 0.5D) * 0.5D;
         double j = ((double)random.nextFloat() - 0.5D) * 0.5D;
         int k = random.nextInt(2) * 2 - 1;
         if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this) && (Direction.Axis)state.get(Properties.AXIS) == Direction.Axis.Z) {
            d = (double)pos.getX() + 0.5D + 0.25D * (double)k;
            g = (double)(random.nextFloat() * 2.0F * (float)k);
         } 
         else if (!world.getBlockState(pos.up()).isOf(this) && !world.getBlockState(pos.down()).isOf(this)) {
            e = (double)pos.getY() + 0.5D + 0.25D * (double)k;
            h = (double)(random.nextFloat() * 2.0F * (float)k);
         } else {
            f = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
            j = (double)(random.nextFloat() * 2.0F * (float)k);
         }
         switch(this.getDefaultMapColor().id) {
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
   public boolean canFillWithFluid(BlockView blockView, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      return false;
   }

   @Override
   public boolean canBucketPlace(BlockState blockState, Fluid fluid) {
      return false;
   }
  
   @Environment(EnvType.CLIENT)
   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return ItemStack.EMPTY;
   }
   
   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch(rotation) {
         // use COUNTERCLOCKWISE_90 to rotate around Y axis
         case COUNTERCLOCKWISE_90:
            switch((Direction.Axis)state.get(AXIS)) {
               case Z:
               case X:
                  return (BlockState)state.with(AXIS, Direction.Axis.Y);
               case Y:
                  return (BlockState)state.with(AXIS, Direction.Axis.X);
               default:
                  return state;
            }
         case CLOCKWISE_90:
            switch((Direction.Axis)state.get(AXIS)) {
               case Z:
                  return (BlockState)state.with(AXIS, Direction.Axis.X);
               case X:
               case Y:
                  return (BlockState)state.with(AXIS, Direction.Axis.Z);
               default:
                  return state;
            }
      default:
         return state;
      }
   }
  
   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(AXIS, LIT, WATERLOGGED);
   }

   static {
      LIT = Properties.LIT;
      WATERLOGGED = Properties.WATERLOGGED;
      AXIS = Properties.AXIS;
      X_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
      Z_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
      Y_SHAPE = Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 10.0D, 16.0D);
   }

   @Override
   public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
      return new PortalBlockEntity(pos, state);
   }

   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
         BlockEntityType<T> type) {
      return PortalBlockEntity::tick;
   }
}