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
        localLat: string,
        localLon: string,
        veiculo: VehicleType
    ): Promise<any> {
        const apiKey = this.configService.get<string>('API_KEY'); 

        const origin = `${lat},${lon}`; 
        const destin = `${localLat},${localLon}`; 
        const url = `https://maps.googleapis.com/maps/api/directions/json?alternatives=true&radius=10000&origin=${origin}&destination=${destin}&mode=transit&transit_mode=${veiculo}&key=${apiKey}`;
    
        try {
            const response = await firstValueFrom(this.httpService.get(url));
            return response.data;
        } catch (error) {
            throw new Error('Erro ao buscar rota: ' + error.message);
        }
    }


    async getStation(
        type: string,
        localLat: string,
        localLon: string
    ): Promise<any> {
        const apiKey = this.configService.get<string>('API_KEY'); 

        const destin = `${localLat},${localLon}`; 
        const url = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${destin}&radius=1500&type=${type}&key=${apiKey}`;
    
        try {
            const response = await firstValueFrom(this.httpService.get(url));
            return response.data;
        } catch (error) {
            throw new Error('Erro ao buscar localização: ' + error.message);
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
