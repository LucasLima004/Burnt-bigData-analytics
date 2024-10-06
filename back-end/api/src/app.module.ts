import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { LocaleModule } from './locale/locale.module';
import { LocalidadeController } from './localidade/localidade.controller';
import { LocalidadeService } from './localidade/localidade.service';

@Module({
  imports: [LocaleModule],
  controllers: [AppController, LocalidadeController],
  providers: [AppService, LocalidadeService],
})
export class AppModule {}
