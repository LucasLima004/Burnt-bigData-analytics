import { HttpService } from '@nestjs/axios';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { firstValueFrom } from 'rxjs';
import { VehicleType } from './model/vehicle-type.enum';

@Injectable()
export class LocalidadeService {

    constructor(
        private readonly httpService: HttpService,
        private readonly configService: ConfigService,
    ){}

    async getExpectLocale(
        lat: string,
        lon: string,
        local: string,
        veiculo: VehicleType
    ): Promise<any> {
        const apiKey = this.configService.get<string>('API_KEY'); 

        const origin = `${lat},${lon}`; 
        const url = `https://maps.googleapis.com/maps/api/directions/json?origin=${origin}&destination=${encodeURIComponent(local)}&mode=${veiculo}&key=${apiKey}`;
    
        try {
            const response = await firstValueFrom(this.httpService.get(url));
            return response.data;
        } catch (error) {
            throw new Error('Erro ao buscar rota: ' + error.message);
        }
    }

    async searchPlaces(location: string): Promise<any> {
        const apiKey = this.configService.get<string>('API_KEY');
        const url = `https://maps.googleapis.com/maps/api/place/textsearch/json?query=${encodeURIComponent(location)}&key=${apiKey}`;
    
        try {
            const response = await firstValueFrom(this.httpService.get(url));
            return response.data.results.map(place => ({
                name: place.name,
                latitude: place.geometry.location.lat,
                longitude: place.geometry.location.lng,
                address: place.formatted_address,
            }));            
        } catch (error) {
            throw new Error('Erro ao buscar locais: ' + error.message);
        }
    }
}
