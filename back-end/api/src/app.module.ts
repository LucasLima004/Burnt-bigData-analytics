import { Module } from '@nestjs/common';
import { LocalidadeController } from './localidade/localidade.controller';
import { LocalidadeService } from './localidade/localidade.service';
import { ConfigModule } from '@nestjs/config';
import { HttpModule } from '@nestjs/axios';

@Module({
  imports: [
    ConfigModule.forRoot(),
    HttpModule
  ],
  controllers: [LocalidadeController],
  providers: [LocalidadeService],
})
export class AppModule {}
